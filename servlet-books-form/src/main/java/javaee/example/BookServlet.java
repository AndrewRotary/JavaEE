package javaee.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Radu Dumbrăveanu on 2/18/17.
 */
@WebServlet("/book-form")
public class BookServlet extends HttpServlet {
    Book[] books;

    @Override
    public void init() throws ServletException {
        try {
            books = loadBooks();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer bookId = Integer.valueOf(request.getParameter("id") != null ? request.getParameter("id") : "0");

        request.setAttribute("book", books[bookId]);
        request.setAttribute("id", bookId);

        RequestDispatcher view = request.getRequestDispatcher("book-form.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer bookId = Integer.valueOf(request.getParameter("id") != null ? request.getParameter("id") : "0");

        Book book = books[bookId];
        book.setTitle(request.getParameter("title"));
        book.setEditionNumber(Integer.valueOf(request.getParameter("edition")));
        book.setUrl(new URL(request.getParameter("url")));

        response.sendRedirect("/book-form?id=" + bookId);
    }

    private Book[] loadBooks() throws MalformedURLException {
        Book[] books = new Book[3];

        books[0] = new Book();
        books[0].setTitle("Java EE 7 Essentials: Enterprise Developer Handbook");
        books[0].setAuthors(new String[]{"Arun Gupta"});
        books[0].setEditionNumber(1);
        books[0].setUrl(new URL("https://www.amazon.com/Java-EE-Essentials-Enterprise-Developer/dp/1449370179"));

        books[1] = new Book();
        books[1].setTitle("The Java EE 7 Tutorial: Volume 1");
        books[1].setAuthors(new String[]{"Eric Jendrock", "Ricardo Cervera-Navarro", "Ian Evans", "Kim Haase", "William Markito"});
        books[1].setEditionNumber(5);
        books[1].setUrl(new URL("https://www.amazon.com/Java-EE-Tutorial-5th/dp/0321994922"));

        books[2] = new Book();
        books[2].setTitle("Google");
        books[2].setAuthors(new String[]{"X", "Y"});
        books[2].setEditionNumber(0);
        books[2].setUrl(new URL("https://google.md"));

        return books;
    }

}
