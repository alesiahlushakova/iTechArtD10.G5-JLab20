var titleChecked = false;
var authorChecked = false;
var genreChecked = false;
var publisherChecked = false;
var publishDateChecked = false;
var pageCountChecked = false;
var totalAmountChecked = false;
var isbnChecked = false;
var coverChecked = false;
var descriptionChecked = false;
var commentChecked = false;
var emailChecked = false;
var lastNameChecked = false;
var firstNameChecked = false;


const regMail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

var logRegEx = /\W/;
var regExp = /\d\W/;
var punct = /[.,!?()\\|\[\]`@$^*-+=:;¹#"'_\s></%&*]+/;
var digit = /[0-9]+/;
var isbnReg = /^(?=(?:\D*\d){10}(?:(?:\D*\d){3})?$)[\d-]+$/;
var numberRegEx = /^\d+$/;

var borrowDate = document.getElementById("borrowDate");

var comment = document.getElementById("comment");
var minLength = 1;
var notFoundIndex = -1;

var email = document.getElementById("myInput");
var firstName = document.getElementById("readerName");
var lastName = document.getElementById("readerSurname");
var title = document.getElementById("title");
var author = document.getElementById("author");
var genre = document.getElementById("genre");
var submit = document.getElementById("submit");
var publisher = document.getElementById("publisher");
var publishDate = document.getElementById("publishDate");
var pageCount = document.getElementById("pageCount");
var totalAmount = document.getElementById("totalAmount");
var isbn = document.getElementById("isbn");
var description = document.getElementById("description");
var submitModal = document.getElementById("save");
var submitModal1 = document.getElementById("save1");
var modal = document.getElementById("myModal");
var modal1 = document.getElementById("myModal1");
var btn = document.getElementById("myBtn");
var btn1 = document.getElementById("editButton");
var span = document.getElementById("close");
var span1 = document.getElementById("close1");


var submitChange = function () {
//alert(titleChecked +" "+coverChecked+" "+publisherChecked+" "+pageCountChecked+" "+totalAmountChecked+" "+isbnChecked+" "+descriptionChecked);
    if (titleChecked  && publisherChecked
        && pageCountChecked && totalAmountChecked && isbnChecked && descriptionChecked) {

        submit.disabled = false;
        submit.classList.add("active");
    } else {
        submit.disabled = true;
        submit.classList.remove("active");
    }
};
var submitChangeModal = function () {
    if (emailChecked && lastNameChecked && firstNameChecked) {
        submitModal.disabled = false;
        submitModal.classList.add("active");
    } else {
        submitModal.disabled = true;
        submitModal.classList.remove("active");
    }
};

var submitChangeModal1 = function () {
    if ( emailChecked && lastNameChecked && firstNameChecked) {
        submitModal1.disabled = false;
        submitModal1.classList.add("active");
    } else {
        submitModal1.disabled = true;
        submitModal1.classList.remove("active");
    }
};

var validColor = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
}

var notValidColor = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
}


var checkMail = function () {
    if (regMail.test(String(email).toLowerCase())) {
        notValidColor(title);
        emailChecked = false;
    } else {
        validColor(email);
        emailChecked= true;
    }
    submitChange();
};

var checkTitle = function () {
    if (title.value.search(regExp) > notFoundIndex ||
        title.value.length < minLength) {
        notValidColor(title);
        titleChecked = false;
    } else {
        validColor(title);
        titleChecked = true;
    }
    submitChange();
};

var checkName = function () {
    if (firstName.value.search(punct) > notFoundIndex ||
        firstName.value.search(digit) > notFoundIndex ||
        firstName.value.length < 1) {
        notValidColor(firstName);
        firstNameChecked = false;
    } else {
        validColor(firstName);
        firstNameChecked = true;
    }
    if (lastName.value.search(punct) > notFoundIndex ||
        lastName.value.search(digit) > notFoundIndex ||
        lastName.value.length < 1) {
        notValidColor(lastName);
        lastNameChecked = false;
    } else {
        validColor(lastName);
        lastNameChecked = true;
    }
    submitChangeModal();
};


var checkAuthor = function () {
    if (
        author.value.search(digit) > notFoundIndex ||
        author.value.length < 1) {
        notValidColor(author);
        authorChecked = false;
    } else {
        validColor(author);
        authorChecked = true;
    }

    submitChange();
};


var checkGenre = function () {
    if (genre.value.search(regExp) > notFoundIndex ||
        genre.value.length < minLength) {
        notValidColor(genre);
        genreChecked = false;
    } else {
        validColor(genre);
        genreChecked = true;
    }
    submitChange();
};

var checkPublisher = function () {
    if (publisher.value.search(regExp) > notFoundIndex ||
        publisher.value.length < minLength) {
        notValidColor(publisher);
        publisherChecked = false;
    } else {
        validColor(publisher);
        publisherChecked = true;
    }
    submitChange();
};

var checkPublishDate = function () {
    if (publishDate > new Date()) {
        notValidColor(publishDate);
        publishDateChecked = false;
    } else {
        validColor(publishDate);
        publishDateChecked = true;
    }
    submitChange();
};

var checkPageCount = function () {
    if (pageCount.value.search(numberRegEx) > notFoundIndex) {
        notValidColor(pageCount);
        pageCountChecked = false;
    } else {
        validColor(pageCount);
        pageCountChecked = true;
    }
    submitChange();
};

var checkTotalAmount = function () {
    if (totalAmount.value.search(numberRegEx) > notFoundIndex) {
        notValidColor(totalAmount);
        totalAmountChecked = false;
    } else {
        validColor(totalAmount);
        totalAmountChecked = true;
    }
    submitChange();
};

var checkIsbn = function () {
    if (isbn.value.search(isbnReg) > notFoundIndex ||
        isbn.value.length < minLength) {
        notValidColor(isbn);
        isbnChecked = false;
    } else {
        validColor(isbn);
        isbnChecked = true;
    }
    submitChange();
};

var checkDescription = function () {
    if (description.value.search(regExp) > notFoundIndex) {
        notValidColor(description);
        descriptionChecked = false;
    } else {
        validColor(description);
        descriptionChecked = true;
    }
    submitChange();
};

var checkCover = function () {

    var fuData = document.getElementById('photo');
    var FileUploadPath = fuData.value;
    var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();

    alert(Extension);
    if (Extension === "png" || Extension === "jpg") {

        if (fuData.files && fuData.files[0]) {

            var size = fuData.files[0].size;

            if (size > 2097152) {
                alert("Maximum file size exceeds");
                coverChecked = false;
            } else {
                coverChecked = true;
            }
        }

    } else {
        coverChecked = false;
        alert("Photo only allows file types of PNG, JPG. ");
    }
    submitChange();
}



btn.onclick = function () {
    modal.style.display = "block";
}


span.onclick = function () {
    modal.style.display = "none";
    document.getElementById("comment").value = "";
    document.getElementById("myInput").value = "";
    document.getElementById("readerName").value = "";
    document.getElementById("readerSurname").value = "";
}
var closeWindow = function () {

    modal.style.display = "none";
}
var checkComment = function () {
    if (comment.value.search(regExp) > notFoundIndex) {
        notValidColor(comment);
        commentChecked = false;
    } else {
        validColor(comment);
        commentChecked = true;
    }
    submitChangeModal();
};


btn1.onclick = function () {

    modal1.style.display = "block";
    var id=document.getElementById("orderI").value;

    document.getElementById("orderID").value = id;
}



span1.onclick = function () {
    modal1.style.display = "none";

}
var closeWindow1 = function () {

    modal1.style.display = "none";
}

function myFunction() {
    // Declare variables
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('myInput');
    filter = input.value.toUpperCase();
    ul = document.getElementById("myUL");
    li = ul.getElementsByTagName('li');

    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
    checkMail();
}