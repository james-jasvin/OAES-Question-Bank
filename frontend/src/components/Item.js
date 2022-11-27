import React, { useState } from 'react'

const Item = ({ item, updateItem }) => {
  const [ itemState, setItemState ] = useState(item);

  // State to ensure that all input elements are disabled, i.e. non-editable
  const [ inputsDisabled, setInputsDisabled ] = useState(true);

  const textAreaRowSize = 3
  const textAreaColSize = 100

  const updateItemLocal = async () => {
    /*
      Input Format expected:
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
      }
    */
    const requestItem = {
      item: itemState,
      itemType: itemState.itemType
    }

    // If item update failed then reset the rendered itemState to the correct details that
    // were fetched from the database which is what the "item" object represents
    const updateStatus = await updateItem(requestItem)
    if (updateStatus === false)
      setItemState(item)

    setInputsDisabled(true)
  }

  if (item == null)
    return null; 

  return (
    <div className='m-2 p-3 rounded border item-details'>
      <div className='form-group'>
        {/*
          This checkbox when ticked will enable all Item elements to be updated
          Otherwise they will be read-only elements
        */}
        <span>
          <label htmlFor={`${item.itemId}-input-disabler`}>
            Item Editing
            <input 
              id={`${item.itemId}-input-disabler`}
              className='input-disabler'
              type='checkbox'
              checked={ !inputsDisabled }
              onChange={ event => setInputsDisabled(!event.target.checked) }
            />
          </label>
          
        </span>
      </div>
      {/* Render the Item's details */}

      {/* Item Question */}
      <label htmlFor={`${item.itemId}-item-question`}>
        Question:
      </label>
      <br/>
      <textarea 
        id={`${item.itemId}-item-question`}
        rows={textAreaRowSize}
        cols={textAreaColSize}
        onChange={event => setItemState({...itemState, question: event.target.value})}
        value={itemState.question}
        disabled={inputsDisabled}
      />

      {
        item.itemType === 'MCQ'?
        // Displaying MCQ specific properties
        <div>
          <label htmlFor={`${item.itemId}-item-option1`}>
            Option 1:
          </label>
          <br/>
          <textarea
            id={`${item.itemId}-item-option1`}
            rows={textAreaRowSize}
            cols={textAreaColSize}
            onChange={event => setItemState({...itemState, option1: event.target.value})}
            value={ itemState.option1 }
            disabled={inputsDisabled}
          />
          <br/>
          <label htmlFor={`${item.itemId}-item-option2`}>
            Option 2:
          </label>
          <br/>
          <textarea
            id={`${item.itemId}-item-option2`}
            rows={textAreaRowSize}
            cols={textAreaColSize}
            onChange={event => setItemState({...itemState, option2: event.target.value})}
            value={ itemState.option2 }
            disabled={inputsDisabled}
          />
          <br/>
          <label htmlFor={`${item.itemId}-item-option3`}>
            Option 3:
          </label>
          <br/>
          <textarea
            id={`${item.itemId}-item-option3`}
            rows={textAreaRowSize}
            cols={textAreaColSize}
            onChange={event => setItemState({...itemState, option3: event.target.value})}
            value={ itemState.option3 }
            disabled={inputsDisabled}
          />
          <br/>
          <label htmlFor={`${item.itemId}-item-option4`}>
            Option 4:
          </label>
          <br/>
          <textarea
            id={`${item.itemId}-item-option4`}
            rows={textAreaRowSize}
            cols={textAreaColSize}
            onChange={event => setItemState({...itemState, option4: event.target.value})}
            value={ itemState.option4 }
            disabled={inputsDisabled}
          />
          <br/>
          <label htmlFor={`${item.itemId}-item-answer`}>
            Answer:
          </label>
          <br/>
          <input
            id={`${item.itemId}-item-answer`}
            type='number'
            min={1} max={4}
            value={ itemState.answer }
            onChange={event => setItemState({...itemState, answer: event.target.value})}
            disabled={inputsDisabled}
          />
        </div>
        :
        // Displaying True False specific properties
        <div>
          <label htmlFor={`${item.itemId}-item-answer`}>
            Answer:
            <input
              className='true-false-answer'
              id={`${item.itemId}-item-answer`}
              type='checkbox'
              checked={ itemState.answer }
              onChange={event => setItemState({...itemState, answer: event.target.checked})}
              disabled={inputsDisabled}
            />
          </label>
          <br/>
          
        </div>
      }

      <div>Course Name: { item.course.name }</div>

      {/*
        Submit button that sends a PUT request to the backend to update the Item that has
        been input by the Author into the input elements
        But first the update items checkbox must be checked for this button to be rendered
      */}
      {
        !inputsDisabled &&
        // TODO: ADD ONCLICK EVENT HANDLER HERE
        <button onClick={() => updateItemLocal(itemState)} className='btn btn-dark'>
          Update Item
        </button>
      }
      
    </div>
  )
}

export default Item