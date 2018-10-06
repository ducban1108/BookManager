package ducban.deptrai.comot.khonghai.sampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ducban.deptrai.comot.khonghai.sampleproject.adapter.NguoiDungAdapter;
import ducban.deptrai.comot.khonghai.sampleproject.adapter.SachAdapter;
import ducban.deptrai.comot.khonghai.sampleproject.database.DatabaseHelper;
import ducban.deptrai.comot.khonghai.sampleproject.model.Book;
import ducban.deptrai.comot.khonghai.sampleproject.model.User;
import ducban.deptrai.comot.khonghai.sampleproject.sqlitedao.BookDAO;
import ducban.deptrai.comot.khonghai.sampleproject.sqlitedao.UserDAO;

public class BookActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SachAdapter sachAdapter;
    private List<Book> bookList;

    private DatabaseHelper databaseHelper;
    private BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();
        databaseHelper = new DatabaseHelper(this);
        bookDAO = new BookDAO(databaseHelper);


        for (int i = 0; i < 10; i++) {
            Book book = new Book("BreakDance","HipHop","Việt Đức","Kim đức","10","10.000đ","1");

            bookDAO.insertBook(book);
        }


        AddRecyclerview();
    }
    void initViews(){
        recyclerView = findViewById(R.id.recyclerviewSach);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
         bookList= new ArrayList<>();
         bookList.clear();
    }

    void AddRecyclerview(){
        bookList = bookDAO.getAllBooks();
        sachAdapter = new SachAdapter(this, bookList, bookDAO);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sachAdapter);
    }
}
