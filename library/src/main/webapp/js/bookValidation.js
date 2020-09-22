var titleChecked = false;
var authorChecked = false;
var genreChecked = false;
var publisherChecked = false;
var publishDateChecked = false;
var pageCountChecked = false;
var totalAmountChecked = false;
var isbnChecked = false;
var descriptionChecked = false;

var logRegEx = /\W/;
var regExp = /\d\W/;
var punct = /[.,!?()\\|\[\]`@$^*-+=:;¹#"'_\s></%&*]+/;
var digit = /[0-9]+/;
var isbnReg = /^(?=(?:\D*\d){10}(?:(?:\D*\d){3})?$)[\d-]+$/;
var numberRegEx=/^[1-9][0-9]{0,2}$/;

var minLength = 1;
var notFoundIndex = -1;

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



var submitChange = function () {

    //  && publisherChecked
    //     && pageCountChecked && totalAmountChecked && isbnChecked && descriptionChecked
    if (titleChecked === true) {

        submit.disabled = false;
        submit.classList.add("active");
        alert(titleChecked);
    } else {
        alert(titleChecked+"((((");
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

var checkTitle= function () {
    if (title.value.search(regExp) > notFoundIndex ||
        title.value.length < minLength) {
        notValidColor(title);
        titleChecked =false;
    } else {
        validColor(title);
        titleChecked = true;
    }
    submitChange();
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


var checkGenre= function () {
    if (genre.value.search(regExp) > notFoundIndex ||
        genre.value.length < minLength) {
        notValidColor(genre);
        genreChecked =false;
    } else {
        validColor(genre);
        genreChecked = true;
    }
    submitChange();
};

var checkPublisher= function () {
    if (publisher.value.search(regExp) > notFoundIndex ||
        publisher.value.length < minLength) {
        notValidColor(publisher);
        publisherChecked =false;
    } else {
        validColor(publisher);
        publisherChecked = true;
    }
    submitChange();
};

var checkPublishDate= function () {
    if (publishDate > new Date() ) {
        notValidColor(publishDate);
        publishDateChecked =false;
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

var checkIsbn= function () {
    if (isbn.value.search(isbnReg) > notFoundIndex ||
        isbn.value.length < minLength) {
        notValidColor(isbn);
        isbnChecked =false;
    } else {
        validColor(isbn);
        isbnChecked = true;
    }
    submitChange();
};

var checkDescription= function () {
    if (description.value.search(regExp) > notFoundIndex ) {
        notValidColor(description);
        descriptionChecked =false;
    } else {
        validColor(description);
        descriptionChecked = true;
    }
    submitChange();
};