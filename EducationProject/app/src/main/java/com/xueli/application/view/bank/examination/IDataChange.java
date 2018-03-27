package com.xueli.application.view.bank.examination;

import com.xueli.application.mode.bean.exam.SectionOption;

import java.util.List;

public interface IDataChange {

    void onDataChange(List<SectionOption> sectionOptions, int position);
}
