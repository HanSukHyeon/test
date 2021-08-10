package com.example.calinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    /**
     * 연/월 텍스트뷰
     */
    private TextView tvDate;
    /**
     * 그리드뷰 어댑터
     */
    private GridAdapter gridAdapter;

    /**
     * 일 저장 할 리스트
     */
    private ArrayList<String> dayList;

    /**
     * 그리드뷰
     */
    private GridView gridView;

    /**
     * 캘린더 변수
     */
    private Calendar mCal;
    boolean clicked=false;
    //연,월,일을 따로 저장
    final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
    final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
    Button btn_next;
    int point_2_index,next_level=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        tvDate = (TextView) findViewById(R.id.tv_date);
        gridView = (GridView) findViewById(R.id.gridview);
        btn_next=(Button)findViewById(R.id.btn_next);
        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);



        //현재 날짜 텍스트뷰에 뿌려줌
        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));

        dayList = new ArrayList<String>();

        mCal = Calendar.getInstance();

        //요일 배치
        dayList= setCalendar_yo(dayList);

        //날짜 배치
        dayList=setCalendar_day(dayList,mCal,date);



        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        //어뎁터를 사용해 적용
        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);

        //아이템을 클릭했을때
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int point_1_index=position;
                if(clicked==false) {
                       gridView.getChildAt(point_1_index).setBackgroundColor(Color.parseColor("#33333333"));

                       point_2_index=point_1_index;
                       clicked=true;
                }
                else{
                    if(point_1_index!=point_2_index){
                        gridView.getChildAt(point_2_index).setBackgroundColor(Color.parseColor("#00000000"));
                        gridView.getChildAt(point_1_index).setBackgroundColor(Color.parseColor("#33333333"));
                        point_2_index=point_1_index;
                    }
                    else{
                        gridView.getChildAt(point_1_index).setBackgroundColor(Color.parseColor("#00000000"));
                        clicked=false;

                    }
                }
            }
        });


        //다음달 버튼을 클릭했을때
        btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                next_level++;
                dayList.clear();
                // year을 올려주기위한 if문
                if((Integer.parseInt(curMonthFormat.format(date))+next_level)>12){
                    int year_num=(Integer.parseInt(curMonthFormat.format(date))+next_level)/12;
                    tvDate.setText((Integer.parseInt(curYearFormat.format(date))+year_num) + "/" + (Integer.parseInt(curMonthFormat.format(date))+next_level)%12);
                }
                else
                {
                    tvDate.setText(curYearFormat.format(date) + "/" + (Integer.parseInt(curMonthFormat.format(date))+next_level));
                }

                dayList= setCalendar_yo(dayList);


                //날짜 배치
                dayList=setCalendar_day_i(dayList,mCal,date,next_level);
                setCalendarDate(mCal.get(Calendar.MONTH) + 1);


                gridAdapter = new GridAdapter(getApplicationContext(), dayList);
                gridView.setAdapter(gridAdapter);

            }
        });


    }





    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }

    }

    public ArrayList<String> setCalendar_yo(ArrayList<String> dayList){
//gridview 요일 표시

        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        return dayList;
    }

    public ArrayList<String> setCalendar_day(ArrayList<String> dayList,Calendar mCal,Date date) {

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date))-1 , 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }

        return dayList;
    }

    public ArrayList<String> setCalendar_day_i(ArrayList<String> dayList,Calendar mCal,Date date,int j) {

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date))+j-1 , 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }

        return dayList;
    }




}