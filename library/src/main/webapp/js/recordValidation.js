
var borrowDateChecked = false;
var commentChecked = false;

var logRegEx = /\W/;
var regExp = /\d\W/;
var punct = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_\s></%&*]+/;
var digit = /[0-9]+/;
var isbnReg = /^(?=(?:\D*\d){10}(?:(?:\D*\d){3})?$)[\d-]+$/;
var numberRegEx=/^[1-9][0-9]{0,2}$/;

var minLength = 1;
var notFoundIndex = -1;


var borrowDate = document.getElementById("borrowDate");

var comment = document.getElementById("comment");



var submitChange = function () {
    if (borrowDateChecked && commentChecked) {
        submit.disabled = false;
        submit.classList.add("active");
    } else {
        submit.disabled = true;
        submit.classList.remove("active");
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


var checkBorrowDate= function () {
    if (borrowDate > new Date() ) {
        notValidColor(borrowDate);
        borrowDateChecked =false;
    } else {
        validColor(borrowDate);
        borrowDateChecked = true;
    }
    submitChange();
};


var checkComment= function () {
    if (comment.value.search(regExp) > notFoundIndex ) {
        notValidColor(comment);
        commentChecked =false;
    } else {
        validColor(comment);
        commentChecked = true;
    }
    submitChange();
};