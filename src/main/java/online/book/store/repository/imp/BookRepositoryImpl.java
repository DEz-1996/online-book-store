package online.book.store.repository.imp;

import java.util.List;
import online.book.store.model.Book;
import online.book.store.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private static final String CANT_SAVE_MSG = "Can't save book: ";
    private static final String CANT_GET_ALL_MSG = "Can't get all books!";
    private static final String SELECT_ALL_QUERY = "SELECT a FROM Book a";
    private final SessionFactory sessionFactory;

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANT_SAVE_MSG + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_ALL_MSG, e);
        }
    }
}
