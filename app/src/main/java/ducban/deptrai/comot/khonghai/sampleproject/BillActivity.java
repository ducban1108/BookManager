package ducban.deptrai.comot.khonghai.sampleproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import ducban.deptrai.comot.khonghai.sampleproject.adapter.HoaDonAdapter;
import ducban.deptrai.comot.khonghai.sampleproject.database.DatabaseHelper;
import ducban.deptrai.comot.khonghai.sampleproject.model.Bill;
import ducban.deptrai.comot.khonghai.sampleproject.sqlitedao.BillDAO;

public class BillActivity extends AppCompatActivity {

    private RecyclerView recyclerviewHD;
    private LinearLayoutManager linearLayoutManager;
    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<Bill> arrayList;


    private BillDAO billDAO;

    private long datePicker = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        billDAO = new BillDAO(new DatabaseHelper(this));

        AnhXa();
        AddRecyclerview();
    }

    void AnhXa() {
        recyclerviewHD = findViewById(R.id.recyclerviewHD);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        arrayList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Bill bill = new Bill(new Random().nextInt(2000) + "", System.currentTimeMillis());
            arrayList.add(bill);
        }


        hoaDonAdapter = new HoaDonAdapter(this, arrayList);
    }

    void AddRecyclerview() {
        recyclerviewHD.setLayoutManager(linearLayoutManager);
        recyclerviewHD.setHasFixedSize(true);
        recyclerviewHD.setAdapter(hoaDonAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_add_bill) {
            Dialog dialog = new Dialog(BillActivity.this);
            dialog.setTitle("Create New Bill");

            dialog.setContentView(R.layout.dialog_create_bill);

            final EditText edtID;
            final Button btnDatePicker;
            Button btnCreate;

            edtID = dialog.findViewById(R.id.edtID);
            btnDatePicker = dialog.findViewById(R.id.btnDatePicker);
            btnCreate = dialog.findViewById(R.id.btnCreate);

            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog(btnDatePicker);
                }
            });

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String billID = edtID.getText().toString().trim();

                    if (billID.length() > 7) {
                        edtID.setError(getString(R.string.notify_max_bill_id_length));
                        return;
                    }
                    if (datePicker < 0) {
                        Toast.makeText(getApplicationContext(), "Pls choose Date first!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    long result = billDAO.insertBill(new Bill(billID, datePicker));

                    if (result < 0){
                    }else {
                    }

                }
            });

            dialog.show();


        }

        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog(final Button btnPicker) {


        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                calendar.set(year, month, day);
                BillActivity.this.datePicker = calendar.getTimeInMillis();
                btnPicker.setText(new Date(calendar.getTimeInMillis()).toString());

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
