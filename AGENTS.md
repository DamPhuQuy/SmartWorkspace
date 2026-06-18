# AGENTS.md

## Project Overview

### Project Name

The project is Smart workspace

### Purpose

The purpose of this project is to build a hybrid smart workspace and club management platform. The system combines club operations, member organization, attendance tracking, group leadership, and Jira-style project/task management in one centralized environment.

### Scope

This project provides a centralized workspace management platform for organizations, clubs, student teams, project groups, and communities.

The system supports:

- Workspace and club management
- Member and role management
- Club leadership and group leadership
- Member grouping inside a workspace or club
- Project and task management
- Jira-style issue/task tracking
- Kanban board and workflow tracking
- Team collaboration and communication
- Comments and activity logs
- File attachments and document sharing
- Notifications and reminders
- Event and meeting management
- Attendance taking for meetings, events, and club sessions
- Dashboard and reporting

The platform is designed for student clubs, organizations, project teams, and communities that need both operational club management and structured project execution. A workspace can represent a club, organization, department, class, or team. Inside a workspace, members can be divided into groups with group leaders, and club/workspace leads can oversee membership, attendance, projects, and task delivery.

Out of scope:

- Video conferencing
- Enterprise ERP features
- Financial/accounting management
- Enterprise human resource management (HRM)
- Customer relationship management (CRM)

### Key Features

#### Workspace Management

- Create and manage multiple workspaces or clubs
- Configure workspace settings and preferences
- Support multi-organization environments

#### Club & Group Management

- Represent a workspace as a club, organization, project team, or community
- Split members into groups, committees, squads, or departments
- Assign group leaders responsible for group-level coordination
- Assign club leads or workspace owners responsible for global club administration
- Support member movement between groups while preserving activity history

#### Member & Role Management

- Invite and manage members
- Role-Based Access Control (RBAC)
- Workspace and project-level permissions
- Group-level leadership and responsibility assignment
- Membership lifecycle states such as invited, active, inactive, or removed

#### Project Management

- Create and organize projects
- Track project progress and status
- Manage project members and responsibilities

#### Task Management

- Create, assign, update, and track Jira-style tasks/issues
- Set priorities, deadlines, and statuses
- Support task dependencies and subtasks
- Support assignees, reporters/creators, watchers, comments, labels, and activity history
- Support workflow status transitions such as backlog, todo, in progress, review, done, and cancelled

#### Kanban Board

- Visual task tracking using Kanban boards
- Drag-and-drop task management
- Custom workflow stages

#### Collaboration

- Task comments and discussions
- Activity logs and audit trails
- Team notifications and mentions

#### Event Management

- Create and manage events and meetings
- Event scheduling and reminders
- Attendance tracking

#### Attendance Management

- Take attendance for events, meetings, and recurring club sessions
- Track attendance status such as pending, present, absent, excused, or late
- Allow authorized leaders to mark attendance for members in their scope
- Support attendance reporting by workspace, group, event, and member

#### File Management

- Upload and share documents
- Attach files to tasks and projects
- Centralized document storage

#### Dashboard & Reporting

- Workspace overview dashboard
- Task and project statistics
- Productivity and progress reports

#### Notification System

- Real-time notifications
- Deadline reminders
- Activity updates

---

## Business Domain

### Domain Description

The Smart Workspace domain focuses on managing clubs, organizations, groups, and project teams within a collaborative digital environment. The platform is hybrid: it supports operational club management, such as membership, groups, leaders, events, and attendance, while also supporting Jira-style work management, such as projects, tasks, boards, workflow statuses, assignments, subtasks, dependencies, and activity tracking.

The goal is to improve transparency, accountability, attendance discipline, and delivery tracking among members while maintaining structured workflows and role-based access control.

### Core Concepts

- **Workspace** – A collaborative environment that contains members, projects, tasks, events, and resources.
- **Club** – A workspace type used for student clubs, communities, and organizations.
- **Member** – A user who participates in one or more workspaces.
- **Group** – A subdivision inside a workspace or club, such as a department, squad, committee, or project group.
- **Group Leader** – A member responsible for managing a group, coordinating members, reviewing progress, and taking attendance within the group.
- **Club Lead / Workspace Lead** – A member with top-level responsibility for a club/workspace, including membership, roles, groups, projects, events, and attendance oversight.
- **Role** – Defines permissions and access levels within a workspace.
- **Project** – A collection of related tasks and activities aimed at achieving specific objectives.
- **Task / Issue** – A Jira-style unit of work assigned to members and tracked through workflow statuses.
- **Board** – A visual representation of task workflows (e.g., Kanban board).
- **Event** – Meetings, activities, or schedules organized within a workspace.
- **Attendance Record** – A record of a member's attendance status for an event, meeting, or session.
- **Comment** – Communication attached to tasks, projects, or events.
- **Notification** – System-generated updates informing members about relevant activities.
- **Activity Log** – A record of important actions performed within the workspace.

### Business Rules

- A member must belong to a workspace before accessing its resources.
- Every workspace must have at least one owner.
- A club/workspace should have at least one club lead or owner responsible for administration.
- A member can belong to zero or more groups inside a workspace, depending on workspace policy.
- A group must belong to exactly one workspace.
- Every active group should have at least one active group leader.
- A group leader can manage only groups and members within their authorized scope unless granted broader permissions.
- Only authorized members can create, modify, or delete projects and tasks.
- A task must belong to exactly one project.
- A task can only be assigned to members of the corresponding workspace.
- Jira-style task workflow transitions must follow the configured board/workflow rules.
- Task status and Kanban column movement must remain consistent.
- Role permissions determine which actions a member can perform.
- All important changes must be recorded in the activity log.
- Deleted workspaces cannot be accessed by members.
- Events must have a valid organizer.
- Attendance can only be recorded for members of the corresponding workspace.
- Attendance can only be marked by authorized members such as club leads, workspace admins, event organizers, or group leaders within scope.
- Attendance changes must be auditable.
- Notifications are generated when significant actions occur, such as task assignment, status changes, or event updates.

---

## Functional Requirements

### User Management

- Users can register and authenticate using email and password.
- Users can update their profile information.
- Users can reset their passwords.
- Users can manage account settings.

### Workspace Management

- Users can create and manage workspaces.
- Workspace owners can invite members.
- Workspace administrators can configure workspace settings.
- Users can join workspaces through invitations.
- Workspaces can be used as clubs, organizations, teams, or project spaces.

### Club & Group Management

- Club leads can create, update, archive, and delete groups.
- Club leads can assign members to groups.
- Club leads can assign or remove group leaders.
- Group leaders can view and manage members within their assigned groups according to permissions.
- Members can view their group membership and group-level activities.

### Role & Permission Management

- The system shall support Role-Based Access Control (RBAC).
- Workspace owners can assign roles to members.
- Permissions shall be enforced on all protected resources.
- Permissions shall support workspace-level, project-level, and group-level scopes.

### Project Management

- Users can create, update, archive, and delete projects.
- Projects can contain multiple tasks.
- Projects can have assigned members.

### Task Management

- Users can create, update, assign, and track tasks.
- Tasks shall support priorities, due dates, and statuses.
- Tasks can contain comments and attachments.
- Tasks can be organized using Kanban boards.
- Tasks should follow Jira-style semantics, including issue status, assignee, creator/reporter, priority, labels, subtasks, dependencies, comments, and activity history.
- Task workflow transitions should be configurable per board or project where practical.

### Collaboration

- Users can comment on tasks.
- Users can mention other members.
- The system shall maintain activity logs.

### Event Management

- Users can create and manage events.
- Members can register attendance.
- The system shall send event reminders.

### Attendance Management

- Authorized users can take attendance for events, meetings, and club sessions.
- Attendance records shall include member, event/session, status, marker, and timestamp.
- Attendance reports shall be available by workspace, group, event, and member.
- Attendance updates shall be recorded in activity logs.

### Notification System

- Users shall receive notifications for relevant activities.
- Notifications shall support real-time delivery.

---

## Non-functional Requirements

### Performance

- API response time should be less than 500 ms for normal operations.
- The system should support at least 500 concurrent users.
- Database queries should be optimized for frequently accessed resources.

### Scalability

- The system should support horizontal scaling.
- Stateless services should be preferred where possible.
- The architecture should support future microservice migration.

### Reliability

- System availability should be at least 99%.
- Data integrity must be maintained during concurrent operations.
- Critical operations must be executed within database transactions.

### Security

- Authentication shall be implemented using JWT.
- Passwords must be securely hashed.
- Access control shall be enforced using RBAC.
- Sensitive data must be protected in transit and at rest.

### Maintainability

- The codebase should follow clean architecture principles.
- Business logic should be separated from infrastructure concerns.
- Unit and integration tests should be supported.

### Observability

- Application logs should be generated for critical events.
- Error tracking and monitoring should be supported.
- Audit logs should be maintained for important actions.

### Usability

- APIs should follow RESTful conventions.
- API documentation should be available through OpenAPI/Swagger.
- Error messages should be clear and consistent.

### Compatibility

- The system should support modern web browsers.
- APIs should be consumable by web and mobile applications.

---

## Architecture

### Architecture Style

Modular Monolith

### High-Level Overview

The system follows a Modular Monolith architecture where all modules are deployed as a single application while maintaining clear boundaries between business domains. Each module encapsulates its own business logic, data access, and API layer to improve maintainability, scalability, and future migration to microservices if needed.

The application is structured using layered architecture principles:

- Presentation Layer (REST API)
- Application Layer (Use Cases)
- Domain Layer (Business Logic)
- Infrastructure Layer (Database, Storage, External Services)

Modules communicate through well-defined interfaces and domain events instead of direct implementation dependencies whenever possible.

### Components

#### Authentication & Authorization Module

Responsibilities:

- User registration and authentication
- JWT access and refresh token management
- Role-Based Access Control (RBAC)
- Permission validation

Dependencies:

- User Module
- Security Infrastructure
- Database

---

#### User & Member Management Module

Responsibilities:

- User profile management
- Workspace membership management
- Member invitations
- Member lifecycle management
- Member grouping and group assignment

Dependencies:

- Authentication Module
- Workspace Module
- Database

---

#### Workspace Management Module

Responsibilities:

- Workspace creation and configuration
- Club/workspace type and settings
- Workspace settings
- Workspace ownership management
- Member access control

Dependencies:

- User Module
- Role Management Module
- Database

---

#### Role & Permission Module

Responsibilities:

- Role definition
- Permission assignment
- Access control rules
- Authorization policies
- Workspace, project, and group-level authorization scope

Dependencies:

- Workspace Module
- Authentication Module
- Database

---

#### Club & Group Management Module

Responsibilities:

- Club/group structure management
- Group creation, update, archive, and deletion
- Group membership assignment
- Group leader assignment
- Group-level access control support

Dependencies:

- Workspace Module
- User & Member Management Module
- Role & Permission Module
- Database

---

#### Project Management Module

Responsibilities:

- Project creation and management
- Project membership
- Project lifecycle management

Dependencies:

- Workspace Module
- User Module
- Database

---

#### Task Management Module

Responsibilities:

- Task creation and assignment
- Jira-style issue lifecycle management
- Task status transitions
- Priority management
- Due date management
- Subtask management
- Task dependency and watcher management
- Workflow consistency between task status and board column

Dependencies:

- Project Module
- User Module
- Database

---

#### Board Management Module

Responsibilities:

- Kanban board management
- Task workflow visualization
- Task ordering and movement

Dependencies:

- Task Module
- Project Module

---

#### Collaboration Module

Responsibilities:

- Comments
- Mentions
- Activity logs

Dependencies:

- User Module
- Task Module
- Project Module

---

#### Event Management Module

Responsibilities:

- Event scheduling
- Attendance tracking
- Meeting management

Dependencies:

- Workspace Module
- User Module

---

#### Attendance Management Module

Responsibilities:

- Attendance session and record management
- Attendance marking by authorized leaders
- Attendance status tracking
- Group and workspace attendance reports
- Attendance audit logging

Dependencies:

- Event Management Module
- Workspace Module
- Club & Group Management Module
- User & Member Management Module
- Activity Log Module

---

#### Notification Module

Responsibilities:

- Real-time notifications
- Email notifications
- Event publishing and consumption
- Attendance reminders and absence notifications

Dependencies:

- User Module
- Task Module
- Event Module
- Attendance Management Module

---

#### File Storage Module

Responsibilities:

- File uploads
- Attachment management
- Storage integration

Dependencies:

- Task Module
- Project Module
- External Storage Provider

---

#### Reporting & Analytics Module

Responsibilities:

- Dashboard statistics
- Productivity metrics
- Workspace reports
- Attendance reports
- Group progress reports
- Jira-style task flow metrics such as backlog count, cycle time, and overdue tasks

Dependencies:

- Project Module
- Task Module
- Attendance Management Module
- Club & Group Management Module
- Activity Log Module

---

## Tech Stack

### Backend

- Java 25
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- Spring Validation
- Gradle
- MapStruct
- Lombok
- OpenAPI / Swagger
- Scalar

### Frontend

- React
- TypeScript
- Vite
- Tailwind CSS
- React Router

### Database

- PostgreSQL
- Redis
- Flyway

### Infrastructure

- Docker
- Docker Compose
- Nginx
- GitHub Actions

### External Services

- Gmail SMTP (Email Notifications)
- Cloudinary (File Storage)
- Google OAuth 2.0 (Social Login)
- Firebase Cloud Messaging (Push Notifications) _(Optional)_

### Testing

- JUnit 5

---

## Design Guidelines & System Specs

Always follow the daylight design language specified in [.agents/DESIGN.md](file:///home/damphuquy/Documents/SmartWorkspace/.agents/DESIGN.md) for all frontend UI developments:
- **Canvas Theme**: Daylight off-white `#f6f5f4`, never clinical pure white.
- **Surface Theme**: White `#ffffff` for cards, panels, and input fields.
- **Accents**: Notion Blue `#0075de` for primary CTAs and links. Accent colors (purple, pink, teal, etc.) are strictly for decorative sticker labels.
- **Borders & Radii**: Pill shape for primary CTAs, `8px` rounded for utility buttons, and `4px` rounded for form inputs.
- **Elevation**: Gentle hairline borders, using soft layered shadows (`notion-shadow-soft`) rather than heavy drop-shadows.
