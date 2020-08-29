package com.itechart.lab.command;



public enum CommandType {

    /**
     * commands.
     */

    EMPTY_COMMAND{
        {
            this.command = new EmptyCommand();
        }
    };


    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}
