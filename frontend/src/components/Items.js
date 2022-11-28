import React from 'react'
import Item from './Item'

/*
  This component is used for rendering the "Items" view which contains each Item of the Author
  Each Item is its own component
  
  items: Collection of items of the given Author
  updateItem: Method that uses the axios service to update the specified item
*/
const Items = ({ items, updateItem }) => {  
  // If there's no Items for the Author, then render nothing. 
  if (items === [])
    return null

  return (
    <div className='m-5 p-2 rounded regular-shadow' id="items">
      <h2 className='ml-2'>Your Items</h2>
      { 
        // Render each Item as its separate component
        items.map(item =>
          <Item
            item={item}
            key={item.itemId}
            updateItem={updateItem}
          /> 
        )
      }
    </div>
  )
}

export default Items