package com.parthib.cryptocurrency;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.parthib.cryptocurrency.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private ViewPager mSlideViewPager;
        private LinearLayout mDotLayout;
        //TEXT VIEW TO HOLD DOT SLIDERS!
        private TextView[] mDots;
        private SliderAdapter sliderAdapter;


    //Buttons
    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;

    private TextView mSplashText;

    //Get Started Button!!
    private Button mGetStartedBtn;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        createAuthListener();
        //VIEWSLIDERS
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        //BUTTONS
        mNextBtn = (Button) findViewById(R.id.nxtBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);

        //GET STARTED BUTTON!!
        mGetStartedBtn = (Button) findViewById(R.id.getStartedBtn);
        //GetStarted Listener To Switch To Login & Register Activity
        mGetStartedBtn.setOnClickListener(v -> openActivity2());



        //SLIDER ADAPTER
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        //Page listener for Shift in position of Pages
        mSlideViewPager.addOnPageChangeListener(viewListener);


        //OnClickListeners
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
            
        });
    }

    @Override
    public  void  onStart(){
         super.onStart();
         if(authStateListener!=null){
             firebaseAuth.addAuthStateListener(authStateListener);
         }
    }
    @Override
    public  void  onStop(){
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

//##################################################################################################
//\\ INTENT OPENERS //\\
//##################################################################################################
    //GET STARTED INTENT OPENER TO LOGIN!
    public void openActivity2(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



//##################################################################################################

//  ADDS DOT SLIDER FOR CODE!
    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentPink));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

                addDotsIndicator(i);

            mCurrentPage = i;

            if(i == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");

            } else if (i == mDots.length -1 ) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("");
                mBackBtn.setText("Back");

            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }


        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public  void createAuthListener(){
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent intent=new Intent(MainActivity.this,CoinActivity.class);
                    startActivity(intent);
                }
            }
        };
    }


}
