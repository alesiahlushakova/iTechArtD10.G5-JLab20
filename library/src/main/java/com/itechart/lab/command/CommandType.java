package com.itechart.lab.command;



public enum CommandType {

    /**
     * commands.
     */

    EMPTY_COMMAND{
        {
            this.command = new EmptyCommand();
        }
    },
    BOOK_LIST{
        {
            this.command = new BookListCommand();
        }
    },
CHANGE_PAGINATION{
    {
        this.command = new ChangePaginationCommand();
    }
},
    BOOK_LIST_FILTERED{
        {
            this.command = new BookListFilteredCommand();
        }
    },
    ADD_AUTHOR {
        {
            this.command = new AddAuthorCommand();
        }
    },
    ADD_GENRE{
        {
            this.command = new AddGenreCommand();
        }
    },
    SHOW_BOOK{
        {

            this.command = new ShowBookCommand();
        }
    },
    ADD_BOOK{
        {this.command  = new AddBookCommand();}
    },
    EDIT_BOOK{
        {
            this.command = new EditBookCommand();
        }
    },
    DELETE_BOOK{
        {
            this.command = new DeleteBookCommand();
        }
    },

    SEARCH_BOOK{
        {
            this.command = new SearchBookCommand();
        }
    };


    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}
