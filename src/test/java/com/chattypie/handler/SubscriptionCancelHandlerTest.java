package com.chattypie.handler;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.chattypie.service.chattypie.chatroom.ChatroomService;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionCancelHandlerTest {

	@Mock
	private ChatroomService mockChatroomService;
	private SubscriptionCancelHandler testedEventHandler;

	@Before
	public void setUp() throws Exception {
		testedEventHandler = new SubscriptionCancelHandler(mockChatroomService);
	}

	@Test
	public void handleSubscriptionCancel_shouldSuspendChatroom() throws Exception {
		//Given
		String testAppmarketAccountId = "test-id-value";
		SubscriptionCancel testCancelEvent = new SubscriptionCancel("some-key", testAppmarketAccountId);

		//When
		APIResult eventResponse = testedEventHandler.handle(testCancelEvent);

		//Then
		verify(mockChatroomService)
			.suspendChatroom(eq(testAppmarketAccountId));

		assertThat(eventResponse.isSuccess())
			.as("Event processed successfully")
			.isTrue();
	}
}