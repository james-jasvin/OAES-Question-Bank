import axios from 'axios'

// The API endpoint where courses are located, Gateway URL and not itemService url
const courseUrl = `http://localhost:8081/course`

// Gets all courses which are in the database
const getCourses = async () => {
  try {
    const response = await axios.get(`${courseUrl}`)
  
    // The .data field would now contain the courses
    return response.data
  }
  catch (exception) {
    return []
  }
}

const exportObject = { getCourses }

export default exportObject