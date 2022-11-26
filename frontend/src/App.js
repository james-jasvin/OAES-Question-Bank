import { useState, useEffect } from 'react'

import loginService from './services/login'
import itemService from './services/items'

import Notification from './components/Notification'
import LoginForm from './components/LoginForm'
import NavBar from './components/NavBar'
import Items from './components/Items'
import ItemForm from './components/ItemForm'

const App = () => {
  // user state will store the logged in user object, if no login has been done yet then it will be null
  const [ user, setUser ] = useState(null)

  // Will store the items of the logged in user
  const [ items, setItems ] = useState([])

  // These states are used to control the notifications that show up at the top of the screen for events like 
  // login, signup, watchlist creation, etc.
  const [ notification, setNotification ] = useState(null)
  const [ notificationType, setNotificationType ] = useState(null)

  // Create a notification at the top of the screen with given message for 10 seconds 
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
      window.localStorage.setItem('loggedInUser', JSON.stringify(userObject))
      
      notificationHandler(`Logged in successfully as ${userObject.name}`, 'success')
      setItems([])
    }
    catch (exception) {
      notificationHandler(`Log in failed, check username and password entered`, 'error')
    }
  }

  // Function that creates a new watchlist using the watchlistObject that is passed to the function
  const createItem = async (itemObject) => {
    try {
      /* Expected Input Format:
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

      console.log(authorAddedItemObject)
      const createdItem = await itemService.createItem(authorAddedItemObject)

      setItems(items.concat(createdItem))

      notificationHandler(`The new item has been added successfully`, 'success')
    }
    catch (exception) {
      notificationHandler(exception.response.data.error, 'error')
    }
  }

  // TODO: UPDATE THIS METHOD TO UPDATEITEMS() METHOD
  // Function that pays a bill using the billObject that is passed to the function
  const payBill = async (billObject) => {
    try {
      // Call payBill() at the backend 
      await itemService.payBill(billObject)

      // On successful completion of the above method, iterate through all the bills and only retain those bills
      // which don't have ID equal to the billObject's ID, i.e. the ID of the bill that's just been paid/deleted
      setItems(items.filter(bill => bill.billId !== billObject.billId))

      notificationHandler(`Successfully paid the "${billObject.description}" bill`, 'success')
    }
    catch (exception) {
      notificationHandler(`Failed to pay the "${billObject.description}" bill successfully`, 'error')
    }
  }

  
  // Effect Hook that fetches a user's bills
  // If "user" state changes, then the new bills must be fetched.
  // This is why "user" is part of the dependency array of this hook
  // MIGHT HAVE TO CHANGE THIS LATER TO PROMISE CHAINING IF IT FAILS
  useEffect(() => {
      async function fetchData() {
        if (user) {
          const items = await itemService.getUserItems(user)
          setItems(items)
        }
      }
      fetchData()
  }, [user])


  // Effect Hook that parses the local storage for 'loggedInUser' and sets the "user" state if a valid match is found
  // This enables user to login automatically without having to type in the credentials. Caching the login if you will.
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
            Academia - Payments
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
        />
      }

      {
        /* Show Items of the Author when login has happened */
        user !== null &&
        <Items
          items={items}
          payBill={payBill}
        />
      }
    </div>
  )
}

export default App;
