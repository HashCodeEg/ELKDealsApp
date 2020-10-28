package com.elkdeals.mobile.ui.account;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.FaqAdapter;
import com.elkdeals.mobile.api.models.FAQAnswer;
import com.elkdeals.mobile.api.models.FAQModel;
import com.elkdeals.mobile.api.models.FAQuestion;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.InfoViewModel;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQ extends BaseFragment {


    public static final String TAG = "FAQ";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private FaqAdapter adapter;
    private InfoViewModel model;

    public FAQ() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void initViews() {
        manager = new LinearLayoutManager(activity);

        if (getActivity() != null)
            model = ViewModelProviders.of(getActivity()).get(InfoViewModel.class);
        else model = ViewModelProviders.of(activity).get(InfoViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        model.getFaqLiveData().observe(this, faqModels -> {
            if (faqModels == null && faqModels.size() == 0)
                return;
            ArrayList<FAQuestion> questions = new ArrayList<>();
            for (FAQModel faq : faqModels) {
                questions.add(new FAQuestion(faq.getQuestion(), new FAQAnswer(faq.getAnswer())));
                adapter = new FaqAdapter(questions);
                recyclerView.setAdapter(adapter);
            }
        });
        recyclerView.setLayoutManager(manager);
        model.getFAQ();
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
