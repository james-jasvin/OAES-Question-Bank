import React, { useState } from 'react'

/*
  This component is used for rendering a single Bills's view
  Show's the Bills details like name, bill amount, due date, etc.
  Also has the payment button next to it, on clicking which, the payment method is executed

  bill: The Bill object that has to be rendered
  payBill: Method that uses the axios service to pay the specified bill, i.e. send a DELETE request

  Note that the key attribute is not written here, its only written in the map() method that renders
  the collection
*/
const Item = ({ item, payBill }) => {
  const [ itemState, setItemState ] = useState(item);

  // State to ensure that all input elements are disabled, i.e. non-editable
  const [ inputsDisabled, setInputsDisabled ] = useState(true);

  if (item == null)
    return null; 

  /*
    Instruments are added to watchlists with the help of a dropdown list <select>.
    Add a <option> element with value=-1 in the <select> list that will serve as the default option.
    "value" property corresponds to index of a watchlist in the filteredWatchlists list.
    When user clicks an entry, the onChange of the <select> is triggered which will call createWatchlistInstrument.
    If user clicks default option, then nothing should happen which is why we check for watchlistIdx == -1
    in the createWatchlistInstrument function.
  */
  return (
    <div>
      <div>
        {/*
          This checkbox when ticked will enable all Item elements to be updated
          Otherwise they will be read-only elements
        */}
        <input 
          type='checkbox'
          checked={ !inputsDisabled }
          onChange={ event => setInputsDisabled(!event.target.checked) }
        />
      </div>

      {/* Render the Item's details */}

      {/* Item Question */}
      <textarea 
        rows={5}
        cols={75}
        onChange={event => setItemState({...itemState, question: event.target.value})}
        value={itemState.question}
        disabled={inputsDisabled}
      />

      {
        item.itemType === 'MCQ'?
        // Displaying MCQ specific properties
        <div>
          <textarea
            rows={5}
            cols={75}
            onChange={event => setItemState({...itemState, option1: event.target.value})}
            value={ itemState.option1 }
            disabled={inputsDisabled}
          />
          <textarea
            rows={5}
            cols={75}
            onChange={event => setItemState({...itemState, option2: event.target.value})}
            value={ itemState.option2 }
            disabled={inputsDisabled}
          />
          <textarea
            rows={5}
            cols={75}
            onChange={event => setItemState({...itemState, option3: event.target.value})}
            value={ itemState.option3 }
            disabled={inputsDisabled}
          />
          <textarea
            rows={5}
            cols={75}
            onChange={event => setItemState({...itemState, option4: event.target.value})}
            value={ itemState.option4 }
            disabled={inputsDisabled}
          />
          <input
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
          <input 
            type='checkbox'
            checked={ itemState.answer }
            onChange={event => setItemState({...itemState, answer: event.target.checked})}
            disabled={inputsDisabled}
          />
        </div>
      }

      Course Name: { item.course.name }

      {/*
        Submit button that sends a PUT request to the backend to update the Item that has
        been input by the Author into the input elements
        But first the update items checkbox must be checked for this button to be rendered
      */}
      {
        !inputsDisabled?
        // TODO: ADD ONCLICK EVENT HANDLER HERE
        <button>
          Update Item
        </button>
        : <></>
      }
      
    </div>
  )
}

export default Item