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
    DELETE_BOOK{
        {
            this.command = new DeleteBookCommand();
        }
    };


    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}
