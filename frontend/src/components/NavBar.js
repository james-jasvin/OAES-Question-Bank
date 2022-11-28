import React from 'react'

/*
  This component is used for rendering the Nav Bar which contains the following,
  - Welcome message for the logged in user
  - Logout Button
*/
const NavBar = ({ user, setUser }) => {
  /*
    If the Logout button has been clicked then clear the loggedInUser object from localStorage 
    and update "user" state to null in order to logout.
    Otherwise on the next reload, the Effect hook will again read the user from the localStorage
    and relogin without showing the login form.
  */
  const logout = () => {
    window.localStorage.removeItem('loggedInUser')
    setUser(null)
  }

  // Prevents Key Exception errors if "user" state hasn't loaded yet
  if (!user)
    return null

  // Fully styled Navbar using Bootstrap (it can be a big pain to style Navbars)
  return (
    <div className='regular-shadow mb-1'>
      <nav className='navbar navbar-expand-lg navbar-dark' id='menu'>
        {/* Display welcome message */}
        <button className='navbar-brand btn btn-link border border-light p-2'>Welcome, {user.name}</button>
        
        {/* Bootstrap element for hamburger menu on collapse */}
        <button
          className='navbar-toggler' type='button'
          data-toggle='collapse' data-target='#navbarSupportedContent'
          aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'
        >
          <span className='navbar-toggler-icon'></span>
        </button>
        
        <div className='collapse navbar-collapse' id='navbarSupportedContent'>
          {/* Filler UL element that pushes the logout to the right of the NavBar */}
          <ul className="navbar-nav mr-auto">
          </ul>
          
          {/* Logout button */}
          <div className='inline my-2 my-lg-0'><button className='btn btn-lg btn-primary' onClick={logout}>Logout</button></div>
        </div>
      </nav>
    </div>
  )
}

export default NavBar