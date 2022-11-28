import { useState, useEffect } from 'react'

import loginService from './services/login'
import itemService from './services/items'
import courseService from './services/courses'


import Notification from './components/Notification'
import LoginForm from './components/LoginForm'
import NavBar from './components/NavBar'
import Items from './components/Items'
import ItemForm from './components/ItemForm'

const App = () => {
  // User state will store the logged in user object
  // If no login has been done yet then it will be null
  const [ user, setUser ] = useState(null)

  // Will store the items of the logged in user
  const [ items, setItems ] = useState([])

  // Will store the courses that are available in the database
  const [ courses, setCourses ] = useState([])

  // These states are used to control the notifications that show up at the top of the screen 
  // for events like login, signup, item creation, etc.
  const [ notification, setNotification ] = useState(null)
  const [ notificationType, setNotificationType ] = useState(null)

  // Creates a notification at the top of the screen with given message for 10 seconds 
  // Notifications are of two types, "error" and "success"
  // The appearance of these two notifications can be adjusted via the index.css file
  const notificationHandler = (message, type) => {
    setNotification(message)
    setNotificationType(type)

    setTimeout(() => {
      setNotificationType(null)
      setNotification(null)
    }, 3000)
  }

  // Function that handles login of users
  const handleLogin = async (credentials) => {
    try {
      const userObject = await loginService.login(credentials)
      setUser(userObject)

      // Cache the user obtained from the user into localStorage so that it can be used
      // for later logins without the user having to input their login details
      window.localStorage.setItem('loggedInUser', JSON.stringify(userObject))
      
      notificationHandler(`Logged in successfully as ${userObject.name}`, 'success')

      // Set items to empty to clear the data from previous login if there was any
      setItems([])
    }
    catch (exception) {
      notificationHandler(`Log in failed, check username and password entered`, 'error')
    }
  }

  // Function that creates a new item using the itemObject that is passed to the function
  const createItem = async (itemObject) => {
    try {
      /* Expected Input Format by the backend:
      {
        "author": {
          "loginId": "gaurav_tirodkar",
          "password": "Gaurav@123",
          "authorId": 2
        },
        "item": <Item Parameters>,
        "itemType": "TrueFalse",
        "courseId": 1
      }
      */
      const authorAddedItemObject = {
        ...itemObject,
        author: user
      }

      const createdItem = await itemService.createItem(authorAddedItemObject)

      // Attach newly created item to the items state
      setItems(items.concat(createdItem))

      notificationHandler(`The new item has been added successfully`, 'success')
    }
    catch (exception) {
      notificationHandler(exception.response.data.error, 'error')
    }
  }

  /*
    Function that updates an item using the itemObject that is passed to the function
    by the Item component.

    Returns true when update happens successfully.
    So that the item state of the item being updated can be rendered with the updated values
    And when update fails, it returns false.
    In which case the item state of the item being updated is reset back to the original values
    that was fetched from the DB
  */
  const updateItem = async (itemObject) => {
    try {
      /*
        Expected Input Format by the backend:
        {
          "author": {
              "loginId": "gaurav_tirodkar",
              "password": "Gaurav@123",
              "authorId": 2
          },
          "item": <Item Parameters>,
          "itemType": "TrueFalse",
        }
      */
      const authorAddedItemObject= {
        author: user,
        ...itemObject
      }

      const updatedItem = await itemService.updateItem(authorAddedItemObject)

      // Update items state by storing the newly updated item in the old item's place
      // and leaving all other items as it is
      setItems(items.map(i => i.itemId !== updatedItem.itemId? i: updatedItem))

      notificationHandler(`Updated the item successfully`, 'success')
      return true
    }
    catch (exception) {
      notificationHandler(`Failed to update the item`, 'error')
      return false
    }
  }
  
  /*
    Effect Hook that fetches a user's bills
    If "user" state changes, then the new items must be fetched.
    This is why "user" is part of the dependency array of this hook
  */
  useEffect(() => {
      async function fetchItems() {
        if (user) {
          const items = await itemService.getUserItems(user)
          setItems(items)
        }
      }
      fetchItems()
  }, [user])

  // Fetch all Courses in the Database
  useEffect(() => {
    async function fetchCourses() {
      const courses = await courseService.getCourses()
      setCourses(courses)
    }
    fetchCourses()
  }, [])

  /*
    Effect Hook that parses the local storage for 'loggedInUser' and sets the "user" 
    state if a valid match is found.
    This enables user to login automatically without having to type in the credentials.
    Caching the login if you will.
  */
  useEffect(() => {
    const loggedInUser = window.localStorage.getItem('loggedInUser')
    if (loggedInUser)
      setUser(JSON.parse(loggedInUser))
  }, [])

  return (
    <div>
      {/* Header of the page */}
      <div className='text-center page-header p-2 regular-text-shadow regular-shadow'>
          <div className='display-4 font-weight-bold'>
            OAES - Item Bank
          </div>
      </div>
      
      {/* Notification component that will render only when the notification state is not null */}
      <Notification notification={notification} type={notificationType} />

      {
        /* Show Login form when no login has happened */
        user === null && 
        <LoginForm startLogin={handleLogin}/>
      }

      {
        /* Show NavBar when login has happened */
        user !== null && 
        <NavBar user={user} setUser={setUser}/>
      } 

      {
        /* Show Item Form on login that can be used to create an item */
        user !== null &&
        <ItemForm
          createItem={createItem}
          courses={courses}
        />
      }

      {
        /* Show Items of the Author when login has happened */
        user !== null &&
        <Items
          items={items}
          updateItem={updateItem}
        />
      }
    </div>
  )
}

export default App;
