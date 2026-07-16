package com.finverse.dao;

import com.finverse.model.ScheduledTransfer;
import java.util.ArrayList;
import java.util.List;

public class ScheduledTransferDAOImpl implements ScheduledTransferDAO {

    private static final List<ScheduledTransfer> schedules = new ArrayList<>();

    @Override
    public void save(ScheduledTransfer transfer) {
        schedules.add(transfer);
    }

    @Override
    public List<ScheduledTransfer> getAll() {
        return schedules;
    }

    @Override
    public List<ScheduledTransfer> getByUser(int userId) {
        List<ScheduledTransfer> list = new ArrayList<>();
        for (ScheduledTransfer s : schedules) {
            if (s.getUserId() == userId) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public void deleteSchedule(ScheduledTransfer transfer) {
        schedules.remove(transfer);
    }

    @Override
    public List<ScheduledTransfer> getAllSchedules() {
        return schedules;
    }

    @Override
    public ScheduledTransfer getSchedule(int scheduleId) {
        for (ScheduledTransfer transfer : schedules) {
            if (transfer.getScheduleId() == scheduleId) {
                return transfer;
            }
        }
        return null;
    }

    @Override
    public List<ScheduledTransfer> getSchedules(int userId) {
        List<ScheduledTransfer> list = new ArrayList<>();
        for (ScheduledTransfer transfer : schedules) {
            if (transfer.getUserId() == userId) {
                list.add(transfer);
            }
        }
        return list;
    }
}