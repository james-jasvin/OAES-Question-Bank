import React, { useState } from 'react'

/*
  This component is used for creating a new Item
  - createItem = Method that will create a new Item, defined in App.js
  - courses = State defined in App.js that contains all the courses fetched from the database
*/
const ItemForm = ({ createItem, courses }) => {
  // Setting up default empty items for each item type so that its easier to initialize states
  const emptyItem = {
    'question': ''
  }

  const emptyMCQItem = {
    'option1': '',
    'option2': '',
    'option3': '',
    'option4': '',
    'answer': 1
  }

  const emptyTrueFalseItem = {
    'answer': true
  }

  // Stores item type independent properties
  const [ item, setItem ] = useState(emptyItem)

  // Stores MCQ specific properties
  const [ mcqItem, setMCQItem ] = useState(emptyMCQItem)

  // Stores True False specific properties
  const [ trueFalseItem, setTrueFalseITem ] = useState(emptyTrueFalseItem)

  // What is the current itemType selected by the User
  // Currently takes two values MCQ and TrueFalse
  const [ itemType, setItemType ] = useState('MCQ')

  /*
    State used for controlling the Select Course dropdown list.
    It stores the index of a course in the courses list/state.
    Initially set to -1, which is used for representing the default "Select a Course" option.
  */
  const [ selectedCourse, setSelectedCourse ] = useState(-1)

  const addItem = async (event) => {
    event.preventDefault()

    // If no valid course selected then return to stop submission
    if (selectedCourse === -1)
      return null

    let requestItem = null

    if (itemType === 'MCQ') {
      /*
        Input Format expected by createItem() method:
        {
          "item": {
            "question": "Dummy question 7?",
            "option1": "Factory Pattern",
            "option2": "Abstract Factory Pattern",
            "option3": "Singleton Pattern",
            "option4": "Transfer Object Pattern",
            "answer": 3
          },
          "itemType": "MCQ",
          "courseId": 1
        }
      */
      requestItem = {
        item: {
          question: item.question,
          ...mcqItem
        },
        itemType: itemType,
        courseId: courses[selectedCourse].courseId
      }
    }
    else {
      /*
        Input Format expected by createItem():
        {
          "item": {
            "question": "Dummy question 6?",
            "answer": false
          },
          "itemType": "TrueFalse",
        }
      */
      requestItem = {
        item: {
          question: item.question,
          ...trueFalseItem
        },
        itemType: itemType,
        courseId: courses[selectedCourse].courseId
      }
    }

    await createItem(requestItem)

    // Reset all form states to initial values, so that the form is reset
    setItem(emptyItem)
    setItemType('MCQ')
    setMCQItem(emptyMCQItem)
    setTrueFalseITem(emptyTrueFalseItem)
  }

  // For handling selection of drop-down event
  const handleSelectCourse = (event) => {
    const courseIdx = event.target.value
    // Set selectedCourse option so that its displayed to the user on the frontend
    setSelectedCourse(courseIdx)
  }


  return (
    <div className='p-2 m-5 rounded regular-shadow' id='item-form-div'>
      <h3>Add a New Item</h3>

      <form onSubmit={addItem} id='item-form' data-testid='item-form'>
        {/* Enter Question for the Item */}
        <div className='form-group'>
          <label htmlFor='item-question'>Question:</label>
          <input
            className='form-control'
            value={item.question}
            onChange={event => setItem({...item, question: event.target.value})}
            placeholder='Enter question'
            type='text'
            id='item-question'
            required
          />
        </div>

        {/* Manage Item Type here, currently can be MCQ or TrueFalse */}
        <label>Item Type:</label>
        <div className='form-radio-group' onChange={event => setItemType(event.target.value)}>
          <input required type='radio' id='mcq' name='item-type' value='MCQ' className='mr-2' data-testid='mcq-radio-button'/>
          <label htmlFor='mcq'>MCQ Item</label><br/>
          <input type='radio' id='true-false' name='item-type' value='TrueFalse' className='mr-2'/>
          <label htmlFor='true-false'>True False Item</label><br/>
        </div>

        {
          itemType === 'MCQ'?
          // MCQ Item related inputs
            <div>
              <div className='form-group'>
                <label htmlFor='mcq-item-option1'>Option 1:</label>
                <input
                  className='form-control'
                  value={mcqItem.option1}
                  onChange={event => setMCQItem({...mcqItem, option1: event.target.value})}
                  placeholder='Enter option 1'
                  type='text'
                  id='item-option1'
                  required
                />
              </div>
              <div className='form-group'>
                <label htmlFor='mcq-item-option2'>Option 2:</label>
                <input
                  className='form-control'
                  value={mcqItem.option2}
                  onChange={event => setMCQItem({...mcqItem, option2: event.target.value})}
                  placeholder='Enter option 2'
                  type='text'
                  id='item-option2'
                  required
                />
              </div>
              <div className='form-group'>
                <label htmlFor='mcq-item-option3'>Option 3:</label>
                <input
                  className='form-control'
                  value={mcqItem.option3}
                  onChange={event => setMCQItem({...mcqItem, option3: event.target.value})}
                  placeholder='Enter option 3'
                  type='text'
                  id='item-option3'
                  required
                />
              </div>
              <div className='form-group'>
                <label htmlFor='mcq-item-option4'>Option 4:</label>
                <input
                  className='form-control'
                  value={mcqItem.option4}
                  onChange={event => setMCQItem({...mcqItem, option4: event.target.value})}
                  placeholder='Enter option 4'
                  type='text'
                  id='item-option4'
                  required
                />
              </div>
              <div className='form-group'>
                <label htmlFor='mcq-item-answer'>Answer:</label>
                <input
                  type='number'
                  id='mcq-item-answer'
                  min={1} max={4}
                  value={ mcqItem.answer }
                  onChange={event => setMCQItem({...mcqItem, answer: event.target.value})}
                />
              </div>
            </div>
            :
            // True False Item related inputs
            <div>
              <div className='form-group'>
                <label htmlFor='true-false-item-answer'>Answer:</label>
                <input 
                  type='checkbox'
                  id='true-false-item-answer'
                  checked={ trueFalseItem.answer }
                  onChange={event => setTrueFalseITem({...trueFalseItem, answer: event.target.checked})}
                />
              </div>
            </div>
        }

        {/* Drop-down for Selecting Course */}
        <div className='form-group'>
        <label>
          Select Course: <br/>
          {/* Select Drop Down for choosing Course of the Item being added */}
          <select 
            className='form-select p-2 regular-shadow rounded-lg'
            value={selectedCourse}
            onChange={handleSelectCourse}
          >
            <option value={-1}>Select a Course</option>
            {
              courses.map((c, idx) => 
                <option key={c.courseId} value={idx}>{c.name}</option>
              )
            }
          </select>
        </label>
        </div>
        
        {/* Submit Button for creating Item */}
        <div>
          <button className='btn btn-success btn-lg regular-shadow' type='submit' id='item-submit'>Submit</button>
        </div>
        
      </form>
    </div>
  )
}

export default ItemForm