package com.constantinkoehler.rxsoccer.gameDetails;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.utils.CalendarUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameDetailsActivity extends AppCompatActivity {

    private Game game;
    public static String GAME_EXTRA = "GAME_EXTRA";

    @BindView(R.id.venueTV)
    TextView venueTextView;
    @BindView(R.id.competitionTV)
    TextView competitionTextView;
    @BindView(R.id.dateTV)
    TextView dateTextView;

    @BindView(R.id.fullTimeOrKickoffTV)
    TextView fullTimeOrKickoffTextView;
    @BindView(R.id.scoreOrTimeTV)
    TextView scoreOrTimeTV;
    @BindView(R.id.usTeamTV)
    TextView usTeamTextView;
    @BindView(R.id.opponentIV)
    ImageView opponentFlagView;
    @BindView(R.id.opponentTV)
    TextView opponentTextView;

    @BindView(R.id.addToCal)
    ImageView addToCallButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = (Game) getIntent().getSerializableExtra(GAME_EXTRA);
        setContentView(R.layout.game_details);
        ButterKnife.bind(this);

        setUpView();
    }

    public void setUpView() {
        venueTextView.setText(game.getVenue());

        if (!TextUtils.isEmpty(game.getCompetition())) {
            competitionTextView.setText(game.getCompetition());
        } else {
            competitionTextView.setVisibility(View.GONE);
        }

        dateTextView.setText(game.getFormattedDateString());

        usTeamTextView.setText(game.getUsTeam());

        opponentTextView.setText(game.getOpponentTeam());

        Picasso.get().load(game.getFlagResource(GameDetailsActivity.this)).into(opponentFlagView);

        scoreOrTimeTV.setText(game.getResultString());

        fullTimeOrKickoffTextView.setText(game.isMatchComplete() ? R.string.fulltime : R.string.kickoff);

        if(game.isMatchComplete()){
            addToCallButton.setVisibility(View.GONE);
        }

        addToCallButton.setOnClickListener(v -> {
            CalendarUtil.addGameToCalendar(game,GameDetailsActivity.this);
        });

    }
}
