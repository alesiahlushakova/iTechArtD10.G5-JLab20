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
    };


    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}
