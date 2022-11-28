import React, { useState } from 'react'

/*
  This component renders the Login Form with all its functionalities
  startLogin is the method that uses the axios service to submit login credentials to the backend
*/
const LoginForm = ({ startLogin }) => {
  // States for tracking form input which are the username and password
  const [ username, setUsername ] = useState('')
  const [ password, setPassword ] = useState('')

  // onSubmit event handler of this form
  const handleLogin = (event) => {
    // Preventing default submission of the form to the action attribute URL
    event.preventDefault()

    const credentials = {
      loginId: username, password
    }

    // Calling startLogin with the provided credentials that will make a call to the backend
    startLogin(credentials)

    // Reset the form state, 
    // i.e. the text that's on the username and password text boxes to empty strings
    setUsername('')
    setPassword('')
  }

  return (
    <div className='form-container'>
      <div className='form-box regular-shadow'>

        {/* Render User Icon on top of the Login form */}
        <div className='header-form'>
          <h4 className='text-primary text-center'>
            <i className='fa fa-user-circle' style={{fontSize:'110px', color: 'lightblue'}}></i>
          </h4>
          <div className='image'></div>
        </div>

        <div className='body-form'>
          <form onSubmit={handleLogin} id='login-form'>

            <div className='input-group mb-3'>
              {/* Renders User Icon next to username input */}
              <div className='input-group-prepend'>
                <span className='input-group-text'><i className='fa fa-user'></i></span>
              </div>
              {/* Username input */}
              <input 
                className='login-form-input'
                type='text'
                placeholder='Username'
                value={username}
                onChange={event => setUsername(event.target.value)}
                id='username'
                required
              />
            </div>

            <div className='input-group mb-3'>
              {/* Renders password icon next to the input */}
              <div className='input-group-prepend'>
                <span className='input-group-text'><i className='fa fa-lock'></i></span>
              </div>
              {/* Password input */}
              <input
                className='login-form-input'
                type='password'
                placeholder='Password'
                value={password}
                onChange={event => setPassword(event.target.value)}
                id='password'
                required
              />
            </div>

            {/* Submit button for the form */}
            <button type='submit' className='btn btn-primary btn-block' id='login-submit'>LOGIN</button>
          </form>
        </div>
      </div>
    </div>   
  )
}

export default LoginForm