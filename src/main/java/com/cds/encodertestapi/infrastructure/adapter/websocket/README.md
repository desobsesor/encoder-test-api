# WebSocket Notification System

This module implements a real-time notification system using WebSocket to inform web clients immediately after successful login events. The system follows clean architecture principles and maintains modularity by placing WebSocket-related logic in independent and appropriately named files.

## WebSocket Architecture

### Integration with the Login Flow

The WebSocket notification mechanism integrates with the authentication flow as follows:

1. A user sends login credentials to the `/api/auth/login` endpoint
2. The `AuthController` processes the request and calls the `AuthenticationService`
3. After successful authentication, the `AuthenticationServiceImpl` generates a JWT token
4. The service then triggers a WebSocket notification via the `WebSocketMessageService`
5. The notification is sent to all connected clients subscribed to the `/topic/login-notifications` topic
6. Clients receive real-time notifications containing the username and the timestamp of the login event

### Key Components

- **WebSocketConfig**: Configures the WebSocket endpoints and the message broker
- **WebSocketMessageService**: Handles sending notifications to connected clients
- **WebSocketController**: Provides endpoints for WebSocket connectivity testing
- **LoginNotificationMessage**: DTO to structure login notification messages
- **AuthenticationSuccessListener**: Listens for successful authentication events to trigger WebSocket notifications

### Client Integration

To connect to the WebSocket and receive login notifications, clients can use the following JavaScript code:

```javascript
// Include the SockJS and STOMP client libraries
// <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

// Connect to the WebSocket
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

// Connect and subscribe to login notifications
stompClient.connect({}, function(frame) {
  console.log('Connected to WebSocket: ' + frame);
  
  // Subscribe to login notifications
  stompClient.subscribe('/topic/login-notifications', function(notification) {
    const loginEvent = JSON.parse(notification.body);
    console.log('Login notification received:', loginEvent);
    
    // Handle the notification (e.g., show a toast message)
    displayNotification(loginEvent);
  });
});

// Example function to display notifications
function displayNotification(loginEvent) {
  const notificationElement = document.createElement('div');
  notificationElement.className = 'notification';
  notificationElement.innerHTML = `
    <strong>${loginEvent.username}</strong> logged in at 
    ${new Date(loginEvent.timestamp).toLocaleTimeString()}
  `;
  
  document.getElementById('notifications-container').appendChild(notificationElement);
}