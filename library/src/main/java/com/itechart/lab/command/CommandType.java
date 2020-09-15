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
    ADD_ORDER{
        {
            this.command = new AddRecordCommand();
        }
    },
    SEARCH_BOOK{
        {
            this.command = new SearchBookCommand();
        }
    },
    EDIT_RECORD{
        {this.command = new EditRecordCommand();}
    };


    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}
