let mcqItems = [], trueFalseItems = [];

const baseURL = "http://localhost:8081/OAES_QuestionBank_Webapp_war_exploded/api/"

function onLoginPageLoad() {
    // If user was already logged in and data is retained in localStorage, then directly take user to Bills.html
    if (window.localStorage.hasOwnProperty("authorId"))
        location.replace("items.html");

    let form = document.getElementById("loginForm");
}

async function handleLogin(event, form) {
    event.preventDefault();

    let loginId = document.getElementById("loginId").value;
    let password = document.getElementById("password").value;

    let author = {
        "loginId": loginId,
        "password": password
    }

    const response = await fetch(baseURL + "author/login", {
        method: "POST",
        body: JSON.stringify(author),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    })

    // If login failed, then non-200 status code will be returned
    if (response.status !== 200) {
        Swal.fire({
            title: 'Login',
            text: 'Login Failed!',
            icon: 'error',
            confirmButtonText: 'Okay'
        });

        form.reset();
        return;
    }

    const loggedInAuthor = await response.json();

    Swal.fire({
        title: 'Login',
        text: 'Login Successful!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })

    window.localStorage.setItem("authorId", loggedInAuthor.authorId);
    window.localStorage.setItem("loginId", loggedInAuthor.loginId);
    window.localStorage.setItem("password", loggedInAuthor.password);
    window.localStorage.setItem("name", loggedInAuthor.name);

    location.replace("items.html");
}

async function onItemsPageLoad() {
    // Fetch the Author's Items
    const authorId = parseInt(window.localStorage.getItem("authorId"));

    const responseItems = await fetch(baseURL + "items?authorId=" + authorId, {
        method: "GET"
    })

    const authorItems = await responseItems.json();
    console.log(authorItems);

    authorItems.forEach(item => {
        if (item.itemType === "MCQ")
            mcqItems.push(item);
        else if (item.itemType === "TrueFalse")
            trueFalseItems.push(item);
    });

    let mcqDashboard = document.getElementById("mcqItemsDashboard");
    let trueFalseDashboard = document.getElementById("trueFalseItemsDashboard");

    if (mcqItems.length === 0)
        mcqDashboard.innerHTML = "No MCQ Items Created";
    else {
        mcqDashboard.innerHTML = "";

        mcqItems.forEach(mcqItem => {
            mcqDashboard.innerHTML += getMCQItemHTML(mcqItem);
        });
    }

    if (trueFalseItems.length === 0)
        trueFalseDashboard.innerHTML = "No True False Items Created";
    else {
        trueFalseDashboard.innerHTML = "";

        trueFalseItems.forEach(trueFalseItem => {
            trueFalseDashboard.innerHTML += getTrueFalseItemHTML(trueFalseItem);
        });
    }
}

function getMCQItemHTML(mcqItem) {
    return "<div class='mcqItem'>" +
            "Item ID: " + mcqItem.itemId + "<br>" +
            "Question: " + mcqItem.question + "<br>" +
            "Option 1: " + mcqItem.option1 + "<br>" +
            "Option 2: " + mcqItem.option2 + "<br>" +
            "Option 3: " + mcqItem.option3 + "<br>" +
            "Option 4: " + mcqItem.option4 + "<br>" +
            "Correct Option: " + mcqItem.answer + "<br>" +
            "Course Name: " + mcqItem.course.name +
        "</div><br>";
}

function getTrueFalseItemHTML(trueFalseItem) {
    return "<div class='trueFalseItem'>" +
        "Item ID: " + trueFalseItem.itemId + "<br>" +
        "Question: " + trueFalseItem.question + "<br>" +
        "Correct Answer: " + trueFalseItem.answer + "<br>" +
        "Course Name: " + trueFalseItem.course.name +
        "</div><br>";
}

async function handleMCQItemCreation(event, form) {
    event.preventDefault();

    let question = document.getElementById("createQuestionInput").value;
    let option1 = document.getElementById("createOption1").value;
    let option2 = document.getElementById("createOption2").value;
    let option3 = document.getElementById("createOption3").value;
    let option4 = document.getElementById("createOption4").value;
    let answer = parseInt(document.getElementById("createAnswer").value);
    let courseId = parseInt(document.getElementById("createCourseId").value);

    const authorId = parseInt(window.localStorage.getItem("authorId"));
    const loginId = window.localStorage.getItem("loginId");
    const password = window.localStorage.getItem("password");

    let author = { authorId, loginId, password }
    let item = { question, option1, option2, option3, option4, answer }

    let itemCreationJSON = {
        author,
        item,
        courseId,
        itemType: "MCQ"
    }

    const response = await fetch(baseURL + "items", {
        method: "POST",
        body: JSON.stringify(itemCreationJSON),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    })

    // If item creation failed, then non-200 status code will be returned
    // NOTE: This should be updated to non-201 status code later on
    if (response.status !== 200) {
        Swal.fire({
            title: 'Item Creation',
            text: 'Item Creation Failed!',
            icon: 'error',
            confirmButtonText: 'Okay'
        });

        return;
    }

    Swal.fire({
        title: 'Item Creation',
        text: 'Item Creation Successful, Reload to View Changes!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })

    form.reset()
}

async function handleTrueFalseItemCreation(event, form) {
    event.preventDefault();

    let question = document.getElementById("createQuestionInputTF").value;
    let answer = parseInt(document.getElementById("createAnswerTF").value) !== 0;
    let courseId = parseInt(document.getElementById("createCourseIdTF").value);

    const authorId = parseInt(window.localStorage.getItem("authorId"));
    const loginId = window.localStorage.getItem("loginId");
    const password = window.localStorage.getItem("password");

    let author = { authorId, loginId, password }
    let item = { question, answer }

    let itemCreationJSON = {
        author,
        item,
        courseId,
        itemType: "TrueFalse"
    }

    const response = await fetch(baseURL + "items", {
        method: "POST",
        body: JSON.stringify(itemCreationJSON),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    })

    // If item creation failed, then non-200 status code will be returned
    // NOTE: This should be updated to non-201 status code later on
    if (response.status !== 200) {
        Swal.fire({
            title: 'Item Creation',
            text: 'Item Creation Failed!',
            icon: 'error',
            confirmButtonText: 'Okay'
        });

        return;
    }

    Swal.fire({
        title: 'Item Creation',
        text: 'Item Creation Successful, Reload to View Changes!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })

    form.reset()
}

async function handleMCQItemUpdate(event, form) {
    event.preventDefault();

    let itemId = document.getElementById("updateItemId").value;
    let question = document.getElementById("updateQuestionInput").value;
    let option1 = document.getElementById("updateOption1").value;
    let option2 = document.getElementById("updateOption2").value;
    let option3 = document.getElementById("updateOption3").value;
    let option4 = document.getElementById("updateOption4").value;
    let answer = parseInt(document.getElementById("updateAnswer").value);

    if (question === "")
        question = null
    if (option1 === "")
        option1 = null
    if (option2 === "")
        option2 = null
    if (option3 === "")
        option3 = null
    if (option4 === "")
        option4 = null
    if (answer == "" || answer === -1)
        answer = null

    const authorId = parseInt(window.localStorage.getItem("authorId"));
    const loginId = window.localStorage.getItem("loginId");
    const password = window.localStorage.getItem("password");

    let author = { authorId, loginId, password }
    let item = { itemId, question, option1, option2, option3, option4, answer }

    let itemUpdateJSON = {
        author,
        item,
        itemType: "MCQ"
    }

    const response = await fetch(baseURL + "items", {
        method: "PUT",
        body: JSON.stringify(itemUpdateJSON),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    })

    // If item creation failed, then non-204 status code will be returned
    if (response.status !== 204) {
        Swal.fire({
            title: 'Item Update',
            text: 'Item Update Failed!',
            icon: 'error',
            confirmButtonText: 'Okay'
        });

        return;
    }

    form.reset()

    Swal.fire({
        title: 'Item Update',
        text: 'Item Update Successful, Reload to View Changes!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })
}

async function handleTrueFalseItemUpdate(event, form) {
    event.preventDefault();

    let itemId = document.getElementById("updateItemIdTF").value;
    let question = document.getElementById("updateQuestionInputTF").value;
    let answer = document.getElementById("updateAnswerTF").value;

    if (question === "")
        question = null

    if (answer === "")
        answer = null
    else {
        answer = parseInt(answer)

        if (answer === -1)
            answer = null
        else
            answer = answer !== 0
    }

    const authorId = parseInt(window.localStorage.getItem("authorId"));
    const loginId = window.localStorage.getItem("loginId");
    const password = window.localStorage.getItem("password");

    let author = { authorId, loginId, password }
    let item = { itemId, question, answer }

    let itemUpdateJSON = {
        author,
        item,
        itemType: "TrueFalse"
    }

    const response = await fetch(baseURL + "items", {
        method: "PUT",
        body: JSON.stringify(itemUpdateJSON),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    })

    // If item creation failed, then non-204 status code will be returned
    if (response.status !== 204) {
        Swal.fire({
            title: 'Item Update',
            text: 'Item Update Failed!',
            icon: 'error',
            confirmButtonText: 'Okay'
        });

        return;
    }

    form.reset()

    Swal.fire({
        title: 'Item Update',
        text: 'Item Update Successful, Reload to View Changes!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })
}



//     billList = null;
//     paymentDictionary = {};
//
//     // If user hasn't logged in and tries to access Bills.html then they'll redirected to Login screen
//     if (!window.localStorage.hasOwnProperty("studentId"))
//         location.replace("index.html");
//
//     let studentId = parseInt(window.localStorage.getItem("studentId"));
//     let name = window.localStorage.getItem("name");
//
//     document.getElementById("welcomeHeading").innerText = "Welcome " + name + ",";
//
//     document.getElementById("totalAmount").innerHTML = "0";
//
//     fetch("http://localhost:8080/Implementation_war/api/bill/get/" + studentId, {
//         method: "GET"
//     })
//         .then((response) => response.json())
//         .then(bills => {
//             if (bills.length === 0) {
//                 document.getElementById("billDataTbody").remove();
//                 document.getElementById("billsHeading").innerText = "No Bills Due";
//                 document.getElementById("paymentButton").style.display = "none";
//             }
//             else {
//                 document.getElementById("billsHeading").innerText = "Bills Due:";
//
//                 let tbody = document.getElementById("billDataTbody");
//
//                 tbody.innerHTML = "";
//
//                 for (let i = 0; i < bills.length; i++) {
//                     let curRow = "";
//                     let id = bills[i].billId;
//
//                     curRow += '<td>' + id + '</td>';
//                     curRow += '<td>' + bills[i].amount + '</td>';
//                     curRow += '<td>' + bills[i].billDate + '</td>';
//                     curRow += '<td>' + bills[i].description + '</td>';
//
//                     curRow +=
//                         '<td> <input type="number" value=0 min=0 max='
//                         + bills[i].amount
//                         + ' id="payment'
//                         + id
//                         + '" oninput="calculate('
//                         + id
//                         + ')" onkeyup="imposeMinMax(this, '
//                         + id
//                         + ')"/> </td>';
//
//                     tbody.innerHTML += '<tr>' + curRow + '</tr>';
//                 }
//
//                 billList = bills;
//             }
//         });
// }
//
// function calculate(billId) {
//     paymentDictionary[billId] = parseInt(document.getElementById("payment" + billId).value);
//
//     // If input field left empty after typing something in it first => NaN or if the user filled in 0 then,
//     // delete this bill's key from dictionary
//     if (isNaN(paymentDictionary[billId]) || paymentDictionary[billId] === 0)
//         delete paymentDictionary[billId];
//
//     let totalAmount = 0;
//
//     for (let i=0; i<billList.length; i++) {
//         let id = billList[i].billId;
//
//         if (paymentDictionary.hasOwnProperty(id)) {
//             totalAmount += paymentDictionary[id];
//         }
//     }
//
//     document.getElementById("totalAmount").innerHTML = totalAmount.toString();
// }
//
// function imposeMinMax(el, id){
//     if(el.value !== ""){
//         if(parseInt(el.value) < parseInt(el.min)){
//             el.value = el.min;
//         }
//         if(parseInt(el.value) > parseInt(el.max)){
//             el.value = el.max;
//         }
//     }
//
//     // Recalculate the total amount after considering the change caused by this function
//     calculate(id)
// }
//
// function payment() {
//     let today = new Date().toISOString().slice(0, 10);
//     let receiptDictionary = {};
//
//     for (let billId in paymentDictionary) {
//         if (paymentDictionary.hasOwnProperty(billId) && paymentDictionary[billId] > 0) {
//             receiptDictionary[billId] = {
//                 "amountPaid": paymentDictionary[billId],
//                 "dateOfPayment": today
//             }
//         }
//     }
//
//     if (Object.keys(receiptDictionary).length === 0) {
//         Swal.fire({
//             title: 'Payment Failed',
//             text: 'Enter valid amount/s to pay',
//             icon: 'error',
//             confirmButtonText: 'Okay'
//         })
//         return;
//     }
//
//     fetch("http://localhost:8080/Implementation_war/api/bill/pay", {
//         method: "POST",
//         body: JSON.stringify(receiptDictionary),
//         headers: {
//             "Content-type": "application/json; charset=UTF-8",
//         },
//     })
//         .then((response) => response.status)
//         .then(status =>
//             Swal.fire({
//                 title: 'Payment',
//                 text: 'Payment Successful!',
//                 icon: 'success',
//                 confirmButtonText: 'Okay'
//             })
//         )
//         .then(val =>
//             onBillsPageLoad() // Call the getBills route again to update the payments made)
//         );
// }

function logout() {
    window.localStorage.clear();

    Swal.fire({
        title: 'Log out',
        text: 'Logged Out Successfully!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })
        .then(res => location.replace("index.html"));
}