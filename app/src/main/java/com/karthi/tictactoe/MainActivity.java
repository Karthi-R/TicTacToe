package com.karthi.tictactoe;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Board board = null;
    private int size = 3;
    private Spinner spinner;
    private boolean isGameEnded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_reset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBoard(size);
                Toast.makeText(MainActivity.this, "Restarted", Toast.LENGTH_SHORT).show();
            }
        });

        spinner = findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        initBoard(size);
    }

    void initBoard(int size) {
        initTableLayout(size);
        board = new Board(size, size);
        isGameEnded = false;
    }

    private void initTableLayout(int size) {

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        tableLayout.removeAllViewsInLayout();

        for (int i = 0; i < size; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < size; j++) {
                TextView tv = new TextView(this);
                tv.setText("-");
                tv.setId(Integer.valueOf(String.valueOf(i) + String.valueOf(j)));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(dp2pix(14));
                tv.setWidth(dp2pix(60));
                tv.setHeight(dp2pix(60));
                tv.setTag(R.id.index_row, i);
                tv.setTag(R.id.index_col, j);
                tv.setOnClickListener(this);
                row.addView(tv);
            }
            tableLayout.addView(row, i);
        }
    }

    int dp2pix(int dp) {
        Resources r = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    @Override
    public void onClick(View view) {
        int row = (int) view.getTag(R.id.index_row);
        int col = (int) view.getTag(R.id.index_col);

        if (isGameEnded) {
            initBoard(size);
        }

        board.incrementActionCount();
        String val = board.getCurrentPlayer();

        TextView text = findViewById(Integer.valueOf(String.valueOf(row) + String.valueOf(col)));
        text.setText(val);

        Log.d("ticTacToe", String.format("Row: %d, Col: %d, Val: %s", row, col, val));

        if (board != null) {
            board.setValueAt(row, col, val);

            String msg = "";
            switch (board.isSolved()) {
                case 1:
                    msg = "Player X won";
                    break;
                case 2:
                    msg = "Player O won";
                    break;
                case 3:
                    msg = "Match ended with draw";
                    break;
            }

            if (!TextUtils.isEmpty(msg)) {
                isGameEnded = true;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                size = 3;
                break;
            case 1:
                size = 4;
                break;
            case 2:
                size = 5;
                break;
        }
        initBoard(size);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
