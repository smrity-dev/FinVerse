package com.finverse.dao;

import com.finverse.model.ScheduledTransfer;
import java.util.List;

public interface ScheduledTransferDAO {

    void save(ScheduledTransfer transfer);
    List<ScheduledTransfer> getAll();
    List<ScheduledTransfer> getByUser(int userId);
    void deleteSchedule(ScheduledTransfer transfer);
    List<ScheduledTransfer> getAllSchedules();
    ScheduledTransfer getSchedule(int scheduleId);
    List<ScheduledTransfer> getSchedules(int userId);
    
}