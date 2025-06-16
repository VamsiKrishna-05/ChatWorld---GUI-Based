# 💬 ChatWorld - Java GUI Chat Application

ChatWorld is a desktop-based real-time chat application built using **Java Swing** with a clean and modern GUI. It allows multiple users to connect via LAN and chat in a common window using **socket programming**. A login system using a local `.txt` file stores user credentials for authentication.

---

## ✨ Features

- 🔐 Login / Register system (with user data saved in `users.txt`)
- 💬 Real-time group chat via sockets
- 🖥️ Java Swing-based GUI (Login + Chat Window)
- ✅ Message broadcasting to all connected users
- 📁 Simple file-based credential handling
- 🚀 Clean and professional UI (Nimbus look & feel)

---

## 🗂️ Project Structure

ChatWorld-GUI/
├── src/
│ ├── Server/
│ │ └── ChatServer.java
│ ├── Client/
│ │ ├── ChatClient.java
│ │ └── LoginGUI.java
│ └── Common/
│ └── Message.java

├── users.txt
└── README.md


---

## 🛠️ How to Run

### 1️⃣ Compile the Code

Navigate to the `src/` directory and run:

```bash
javac -d out -cp src src/Common/Message.java src/Server/ChatServer.java src/Client/*.java
```
2️⃣ Start the Server
```
java -cp out Server.ChatServer
```
3️⃣ Run the Client GUI (Login Window)
```
java -cp out Client.LoginGUI
```
🙌 Credits
Developed by Vamsi Krishna B as a personal learning and networking project using Java GUI and socket programming.
📌 Future Enhancements
✅ WebSocket/HTTP version

🔐 Password encryption

🌐 Web-based UI (Now available: ChatWorld-Web)

🗨️ Private 1-on-1 messaging
