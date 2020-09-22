package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.sql.Date;

import static com.itechart.lab.view.MessageManager.*;

public class AddGenreCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddGenreCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            GenreService genreService = new GenreService();

            String genre = request.getParameter("genre");

           boolean isOperationSuccessful = genreService.saveGenre(genre);
            if (!isOperationSuccessful) {
                return new CurrentJsp("/controller?command=book_list",
                        true, GENRE_WAS_NOT_ADDED_MESSAGE_KEY);
            }

            session.setAttribute(IS_RECORD_INSERTED, true);


            return new CurrentJsp("/controller?command=book_list",
                    true, GENRE_WAS_ADDED_MESSAGE_KEY);



        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}
