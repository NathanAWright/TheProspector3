package com.example.nathz.theprospector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

public class RecMsgSwipeAdapter extends FragmentStatePagerAdapter{

    public RecMsgSwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new RecMsg();
        Bundle bundle = new Bundle();

        if (position+1 == 1) {
            bundle.putString("title", "Contact Soon");
            bundle.putString("yesAnswer1", "Excellent, we look forward to seeing you. Enjoy the rest of your [day/night].");
            bundle.putString("yesAnswer2", "Excellent. We will follow up soon, do enjoy the rest of your [day/night]!");
            bundle.putString("noAnswer1", "That will not be a problem sir/ma'am. Thank you for your time. We are likely to have another informative session for persons that took the survey after yourself. Would you like to be informed of the meeting date and time when it is decided?");
            bundle.putString("noAnswer2", "Very well [sir/ma'am] we will remove your data from our system to prevent any accidental contacting. If you change your mind, you may reply here.\nDo enjoy the rest of your [day/night]!");
            bundle.putString("convoStartText", "\n" +
                    "Good morning/afternoon/night, [Person's Name]. Thank you for your recent feed back on the Major Pips survey.\n" +
                    "We understand that you've expressed an interest in receiving information pertaining to increasing your number \n" +
                    "of income sources. A few of our friends have gladly agreed and are willing to share all of the relevant information with you.\n" +
                    "\n" +
                    "(Please understand that no commitments are required, it is entirely your decision to take this information and do with it what you will).\n" +
                    "Our friends have agreed that this upcoming [Day/Month] at [timeAM/PM] will be best for you to meet with a few others who have been contacted like yourself, based on interest shown.\n" +
                    "\n" +
                    "Should we look forward to your attendance?\n" +
                    "\n" +
                    "(Please let us know if you think you have received this in error).");
        }if (position+1 == 2) {
            bundle.putString("title", "Second Chance");
            bundle.putString("neitherResponse", "Very well [sir/ma'am] we will remove your data from our system to prevent any accidental contacting. If you change your mind, you may reply here.\nDo enjoy the rest of your [day/night]!");
            bundle.putString("livePresentationResponse", "Excellent. Please look out for the link around [next Tuesday at 8:00pm?].");
            bundle.putString("recordingsResponse", "Very well, here is the link to the recordings. Thank you for your time.\n" +
                    "\n" +
                    "http://imlpresentation.com/");
            bundle.putString("yesAnswer1", "Excellent, we look forward to seeing you. Enjoy the rest of your [day/night].");
            bundle.putString("noAnswer1", "That will not be a problem sir/ma'am. Thank you for your time.\n" +
                    "We should soon have the same meeting hosted in an online room, for person's who are interested but are bedridden or simply too busy to grace us with their physical presence. We also have recordings that efficiently give you all of the relevant information in a small time frame for you to watch whenever you have time. (However they are not recommended as you will not be able to ask questions.)\n" +
                    "\n" +
                    "Would you like to have the link to the live meeting room(highly recommended over the recordings), or would you like the recording links?");
            bundle.putString("convoStartText", "\n" +
                    "Good morning/afternoon/night, [Person's Name].\n" +
                    "We were told that we should follow up with you when next we arrive at a meeting date for persons who have expressed interest in increasing their number of income sources.\n" +
                    "Our same friends have are willing to meet other persons that have shown interest in the information, and will share all of the relevant information with them.\n" +
                    "Considering that you were previously invited to a similar meet up, we are simply extending the invite to yourself as a courtesy.\n" +
                    "\n" +
                    "(Please understand that no commitments are required, it is entirely your decision to take this information and do with it what you will).\n" +
                    "The agreed date is this upcoming [Day/Month] at [timeAM/PM], suited for other relevant persons for whom this meeting was intended.\n" +
                    "\n" +
                    "Should we look forward to your attendance this time around [sir/ma'am]?\n" +
                    "\n" +
                    "(Please let us know if you think you have received this in error).\n");
        }if (position+1 == 3) {
            bundle.putString("title", "Final Offer\n(Online Presentation)");
            bundle.putString("followUpText", "*Please be reminded of the live presentation scheduled to start in [60 minutes]\n" +
                    "*Be sure to download \"zoom\" from the app store\n" +
                    "*Please be sure to get in at least 10 minutes early, because live rooms are usually filled and you will be denied access if you attempt to enter too late.\n" +
                    "*If you have any audio trouble, please be sure to say so.\n" +
                    "*You will soon receive the link to the room.");
            bundle.putString("convoStartText", "\nGood morning/afternoon/night, [Person's Name]. You have shown interest in getting access to a live online meeting room for the previously mentioned information to be shared. Please look out for the link around [Tuesday/Tomorrow/Today at 8:00pm?] and do enjoy the rest of your [day/night]!" +
                    "\n(Please let us know if you think you have received this in error).");
        }if (position+1 == 4) {
            bundle.putString("title", "Extras");
            bundle.putString("physPresentationFollowUpText", "Good [day/night] [sir/ma'am], just checking up on your status, are you still on route for the meeting later this evening?");
            bundle.putString("recordingsFollowUpText", "Good [day/night] [sir/ ma'am] this is a follow up message to ensure that you have viewed the short recordings, please let us know if you have any feedback ASAP.\n" +
                    "This is our final contact for you, we will now remove your data from our system to prevent any accidental contacting. If you need to, you may reply here.\n" +
                    "Thank you for taking our survey and do enjoy the rest of your [day/night]!");
            bundle.putString("questionsResponse", "I understand your natural curiosity of the business' nature; here is the snippet of information that I am qualified to share, additional questions will have to be answered by experts when you have made contact with them. I apologise for any inconvenience caused.\n" +
                    "\nThe nature of the business revolves around the use of any smart device and the internet's resources to establish financial gains. Physical work/ presence is not required. If you do decide to get involved, you will be able to do this from anywhere in the world as long as you have an internet connection.");
        }

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
