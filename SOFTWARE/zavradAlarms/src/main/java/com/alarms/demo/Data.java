package com.alarms.demo;

	
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Data {
	  private String alarmId;
	   public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setExtractDataQuery(ExtractDataQuery extractDataQuery) {
		this.extractDataQuery = extractDataQuery;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public void setTriggerOperator(String triggerOperator) {
		this.triggerOperator = triggerOperator;
	}

	public void setTriggerValue(String triggerValue) {
		this.triggerValue = triggerValue;
	}

	public void setCheckInterval(String checkInterval) {
		this.checkInterval = checkInterval;
	}

	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}

	public void setAlarmTarget(String alarmTarget) {
		this.alarmTarget = alarmTarget;
	}

	public void setAlarmChannel(String alarmChannel) {
		this.alarmChannel = alarmChannel;
	}

	public void setAlarmUrl(String alarmUrl) {
		this.alarmUrl = alarmUrl;
	}
	private String deviceId;
	 private ExtractDataQuery extractDataQuery;
	   private String alarmMessage;
	    private String triggerOperator;
	   private String triggerValue;
	  private String checkInterval;
	    private DataRequest dataRequest;
	   private String alarmTarget;
	    private String alarmChannel;
	    private String alarmUrl;


	    public String getAlarmId() {
	        return alarmId;
	    }

	    public String getDeviceId() {
	        return deviceId;
	    }

	    public ExtractDataQuery getExtractDataQuery() {
	        return extractDataQuery;
	    }

	    public String getAlarmMessage() {
	        return alarmMessage;
	    }

	    public String getTriggerOperator() {
	        return triggerOperator;
	    }

	    public String getTriggerValue() {
	        return triggerValue;
	    }

	    public String getCheckInterval() {
	        return checkInterval;
	    }

	    public DataRequest getDataRequest() {
	        return dataRequest;
	    }

	    public String getAlarmTarget() {
	        return alarmTarget;
	    }

	    public String getAlarmChannel() {
	        return alarmChannel;
	    }

	    public String getAlarmUrl() {
	        return alarmUrl;
	    }
	    public Data() {
	    }
	    public Data(
	            String alarmId,
	            String deviceId,
	            ExtractDataQuery extractDataQuery,
	            String alarmMessage,
	            String triggerOperator,
	            String triggerValue,
	            String checkInterval,
	            DataRequest dataRequest,
	            String alarmTarget,
	            String alarmChannel,
	            String alarmUrl
	    ) {
	        this.alarmId = alarmId;
	        this.deviceId = deviceId;
	        this.extractDataQuery = extractDataQuery;
	        this.alarmMessage = alarmMessage;
	        this.triggerOperator = triggerOperator;
	        this.triggerValue = triggerValue;
	        this.checkInterval = checkInterval;
	        this.dataRequest = dataRequest;
	        this.alarmTarget = alarmTarget;
	        this.alarmChannel = alarmChannel;
	        this.alarmUrl = alarmUrl;
	    }

	    public Data(String alarmId2, String deviceId2, String payload, String alarmTarget2,
				String alarmMessage2, String alarmUrl2, String alarmChannel2) {
			// TODO Auto-generated constructor stub
		}

		public void setAlarmId(String alarmId) {
			this.alarmId = alarmId;
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
	    public static class ExtractDataQuery {
	        private  String dataFormat;
	        private  String timeColumn;
	        private  String valueColumn;

	        public ExtractDataQuery(){}

	        public ExtractDataQuery(String dataFormat, String timeColumn, String valueColumn) {
	            this.dataFormat = dataFormat;
	            this.timeColumn = timeColumn;
	            this.valueColumn = valueColumn;
	        }

	        public String getDataFormat() {
	            return dataFormat;
	        }

	        public String getTimeColumn() {
	            return timeColumn;
	        }

	        public String getValueColumn() {
	            return valueColumn;
	        }
	    }

	    @JsonIgnoreProperties(ignoreUnknown = true)
	    public static class DataRequest {
	        private  String URI;
	        private  String method;
	        private  Header headers;
	        private  String payload;

	        public DataRequest(){

	        }
	        public DataRequest(String URI, String method, Header headers, String payload) {
	            this.URI = URI;
	            this.method = method;
	            this.headers = headers;
	            this.payload = payload;
	        }

	        public String getURI() {
	            return URI;
	        }

	        public String getMethod() {
	            return method;
	        }

	        public Header getHeaders() {
	            return headers;
	        }

	        public String getPayload() {
	            return payload;
	        }
	    }
	    @JsonIgnoreProperties(ignoreUnknown = true)
	    public static class Header {
	        private String Authorization;
	        private String ContentType;
	        private  String Accept;

	        public Header(){

	        }

	        public Header(String authorization, String contentType, String accept) {
	            this.Authorization = authorization;
	            this.ContentType = contentType;
	            this.Accept = accept;
	        }

	        public String getAuthorization() {
	            return Authorization;
	        }

	        public String getContentType() {
	            return ContentType;
	        }

	        public String getAccept() {
	            return Accept;
	        }
	}
}
