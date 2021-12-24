# Service desk Telegram bot

## Description

### Features

**General:**

- Centralized management of bot subscribers through settings in Service desk system
- Automatic password generation according to policy settings
- Ability to configure multiple bots

**Bot:**

- Authorization with password
- Service calls (incidents, service requests, change requests)
  - Life cycle notifications
  - List of Service calls where subscriber is the applicant
  - Detailed review of a separate Service call
  - Ability to send a message to a specialist with an attachment
  - Confirmation / rejection (with attachment) of the decision
  - assessment of the quality of execution of the Service call solution
- Registration of a new Service call with text and attachments
- Approvals
  - Life cycle notifications
  - Ability to accept or reject
- Bot and notification localization settings

## Requirements

### OS

- RAM: 4 Gb.
- Windows Server 2012, or Windows Server 2012 R2 or Windows Server 2016.
- Microsoft .NET Framework Version 4.0, Version 4.5 with KB2805221 (on Windows 7 with SP1) or KB2805222 (on Windows
  Server 2012), Version 4.5.1, Version 4.5.2., Version 4.6.2 or Version 4.7. Please note that a full installation (as
  opposed to a client profile) is required.

### IIS (Third-party integration)

- Microsoft Internet Information Server (IIS) 7.0, 7.5, 8, 8.5 or 10.
- Features:
  <br>-- Web server:
<br>--- Common HTTP Features: Default Document, Directory Browsing, HTTP Errors, Static Content
<br>--- Health and Diagnostics: HTTP Logging, Custom Logging, ODBC Logging, Request Monitor
<br>--- Performance: Static Content Compression
<br>--- Security: all
<br>--- Application Development: .NET extensibility 3.5, .NET extensibility 4.6, ASP.NET 3.5, ASP.NET 4.6, CGI, ISAPI Extensions, ISAPI Filters, Server Side Includes, WebSocket Protocol
<br>-- Management Tools:
<br>--- IIS Management Console
<br>--- IIS 6 Management Compatibility: all

