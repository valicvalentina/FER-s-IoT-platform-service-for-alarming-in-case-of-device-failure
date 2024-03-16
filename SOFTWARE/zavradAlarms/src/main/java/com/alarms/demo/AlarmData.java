package com.alarms.demo;

public class AlarmData {
    private String alarmId;
    private String deviceId;
    private String payload;
    private String user;
    private String message;
    private String alarmUrl;
    private String alarmChannel;
    public AlarmData() {
    }

    public AlarmData(String alarmId, String deviceId, String payload, String user, String message,String alarmUrl, String alarmChannel) {
        this.alarmId = alarmId;
        this.deviceId = deviceId;
        this.payload = payload;
        this.user = user;
        this.message = message;
        this.alarmUrl = alarmUrl;
        this.setAlarmChannel(alarmChannel);
    }
    public String getAlarmUrl() {
		return alarmUrl;
	}

	public void setAlarmUrl(String alarmUrl) {
		this.alarmUrl = alarmUrl;
	}

	public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getAlarmChannel() {
		return alarmChannel;
	}

	public void setAlarmChannel(String alarmChannel) {
		this.alarmChannel = alarmChannel;
	}
}