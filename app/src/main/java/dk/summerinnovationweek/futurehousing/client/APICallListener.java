package dk.summerinnovationweek.futurehousing.client;


import dk.summerinnovationweek.futurehousing.client.response.Response;

public interface APICallListener
{
	public void onAPICallRespond(APICallTask task, ResponseStatus status, Response<?> response);
	public void onAPICallFail(APICallTask task, ResponseStatus status, Exception exception);
}
