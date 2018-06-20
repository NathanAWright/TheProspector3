package com.example.nathz.theprospector;


import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecMsg extends android.support.v4.app.Fragment {
    RelativeLayout layout;
    TextView conversationStartText, title, yesAnswer1, noAnswer1, yesAnswer2, noAnswer2, convoStart, responsesText, followUpRec, ifYes1, ifYes2, ifNo1, ifNo2, neitherOption, neitherOptionResponse;
    Button convoStarterBtn, yes1Btn, no1Btn, yes2Btn, no2Btn, neitherOptBtn;

    public RecMsg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_msg, container, false);
        title = view.findViewById(R.id.title);
        conversationStartText = view.findViewById(R.id.conversationStartText);
        yesAnswer1 = view.findViewById(R.id.yesAnswer1);
        layout = view.findViewById(R.id.layout);
        noAnswer1 = view.findViewById(R.id.noAnswer1);
        yesAnswer2 = view.findViewById(R.id.yesAnswer2);
        noAnswer2 = view.findViewById(R.id.noAnswer2);
        convoStart = view.findViewById(R.id.convoStartText);
        convoStarterBtn = view.findViewById(R.id.copyConvoStarterButton);
        yes1Btn = view.findViewById(R.id.copyYesResponse1Button);
        yes2Btn = view.findViewById(R.id.copyYes2Response1Button);
        no1Btn = view.findViewById(R.id.copyNoResponse1Button);
        no2Btn = view.findViewById(R.id.copyNoResponse2Button);
        neitherOptBtn = view.findViewById(R.id.neitherOptionButton);
        followUpRec = view.findViewById(R.id.followUpRec);
        ifYes1 = view.findViewById(R.id.ifYes1);
        ifYes2 = view.findViewById(R.id.ifYes2);
        ifNo1 = view.findViewById(R.id.ifNo1);
        ifNo2 = view.findViewById(R.id.ifNo2);
        neitherOption = view.findViewById(R.id.neitherOption);
        neitherOptionResponse = view.findViewById(R.id.neitherOptionResponse);
        responsesText = view.findViewById(R.id.responsesText);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            convoStarterBtn.setTextColor(Color.parseColor("#bbbbbb"));
            yes1Btn.setTextColor(Color.parseColor("#bbbbbb"));
            yes2Btn.setTextColor(Color.parseColor("#bbbbbb"));
            no1Btn.setTextColor(Color.parseColor("#bbbbbb"));
            no2Btn.setTextColor(Color.parseColor("#bbbbbb"));
            neitherOptBtn.setTextColor(Color.parseColor("#bbbbbb"));
        }


        Bundle bundle = getArguments();
        assert bundle != null;
        title.setText(bundle.getString("title"));
        convoStart.setText(bundle.getString("convoStartText"));
        yesAnswer1.setText(bundle.getString("yesAnswer1"));
        yesAnswer2.setText(bundle.getString("yesAnswer2"));
        noAnswer1.setText(bundle.getString("noAnswer1"));
        noAnswer2.setText(bundle.getString("noAnswer2"));

        if (title.getText().toString().equals("Second Chance")){
            ifYes2.setText(R.string.if_prospect_asks_for_live_presentation);
            ifNo2.setText(R.string.if_prospect_asks_for_recordings_link);
            yesAnswer2.setText(bundle.getString("livePresentationResponse"));
            noAnswer2.setText(bundle.getString("recordingsResponse"));
            neitherOption.setVisibility(View.VISIBLE);
            neitherOptBtn.setVisibility(View.VISIBLE);
            neitherOptionResponse.setText(bundle.getString("neitherResponse"));
            yes2Btn.setText(R.string.copy_live_presentation_response);
            yes2Btn.setWidth(0);
            no2Btn.setText(R.string.copy_recordings_response);
        }
        if (title.getText().toString().equals("Final Offer\n(Online Presentation)")){
            yesAnswer1.setText(bundle.getString("followUpText"));
            followUpRec.setVisibility(View.INVISIBLE);
            followUpRec.setTextSize(1);
            responsesText.setText(R.string.follow_up_messages);
            ifNo2.setText("This is the final follow up.\nPlease DELETE YOUR PROSPECT after this step.");
            ifNo2.setTextColor(Color.parseColor("#551111"));
            ifNo2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            yes2Btn.setVisibility(View.INVISIBLE);
            no1Btn.setVisibility(View.INVISIBLE);
            no2Btn.setVisibility(View.INVISIBLE);
            ifNo1.setVisibility(View.INVISIBLE);
            ifYes1.setVisibility(View.INVISIBLE);
            ifYes2.setVisibility(View.INVISIBLE);
            yes1Btn.setText(R.string.copy_messages);
        }
        if (title.getText().toString().equals("Extras")){
            convoStart.setText(bundle.getString("physPresentationFollowUpText"));
            conversationStartText.setText(R.string.physical_presentation_follow_up);
            convoStarterBtn.setText(R.string.copy_follow_up);
            yesAnswer1.setText(bundle.getString("followUpText"));
            yesAnswer1.setText(bundle.getString("recordingsFollowUpText"));
            responsesText.setText(R.string.recordings_follow_up);
            yes1Btn.setText(R.string.copy_follow_up);
            ifYes1.setText(R.string.please_delete_your_prospect);
            ifYes1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ifYes1.setTextColor(Color.parseColor("#551111"));
            ifYes1.setTextSize(16);
            ifNo1.setText(R.string.If_the_Prospect_asks_questions);
            noAnswer1.setText(bundle.getString("questionsResponse"));
            no1Btn.setText(R.string.copy_reply);


            ifNo2.setText("NB. MAKE SURE YOU EDIT ALL SQUARE BRACKETS.\nExample: \"[Person's Name]\" should be \"John\".");
            ifNo2.setTextColor(Color.parseColor("#551111"));
            ifNo2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            neitherOptionResponse.setText("Happy Prospecting,\nMajor Pips out.");
            neitherOptionResponse.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            neitherOptionResponse.setVisibility(View.VISIBLE);
            followUpRec.setVisibility(View.INVISIBLE);
            yes2Btn.setVisibility(View.INVISIBLE);
            no2Btn.setVisibility(View.INVISIBLE);
            ifYes2.setVisibility(View.INVISIBLE);
        }

        yes1Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext());
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", yesAnswer1.getText().toString());
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        yes2Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Objects.requireNonNull(getContext());
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", yesAnswer2.getText().toString());
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        no1Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext());
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", noAnswer1.getText().toString());
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        no2Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext());
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", noAnswer2.getText().toString());
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        convoStarterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext());
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", convoStart.getText().toString());
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        neitherOptBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext());
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", neitherOptionResponse.getText().toString());
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
