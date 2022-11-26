import React from 'react'
import Item from './Item'

/*
  This component is used for rendering the "Bills" view which contains each Bill of the Student
  Each Bill is its own component
  
  bills: Collection of bills of the given Student
  payBill: Method that uses the axios service to pay the specified bill, i.e. send a DELETE request
*/
const Items = ({ items, payBill }) => {  
  // If there's no Items for the student, then render nothing. 
  // Can instead render a message like "No Items Added"
  if (items === [])
    return null

  return (
    <div className='m-5 p-2 rounded regular-shadow' id="bills">
      <h2 className='ml-2'>Your Items</h2>
      { 
        // Render each Bill as its separate component and for this you use the map() method
        // Whenever you use the map() method to render a collection of Components, React requires that you specify a unique
        // attribute of each component in this collection and for this case, we are using the id of each Bill as the key
        // for React to uniquely identify each Bill
        // We also pass on the Bill object that has to be rendered by the component and the payBill method that will
        // execute the payment
        items.map(item =>
          <Item
            item={item}
            key={item.itemId}
            payBill={payBill}
          /> 
        )
      }
    </div>
  )
}

export default Items