# Smart Workspace Entity-Relationship Diagram

This document uses dbdiagram DBML syntax.

```dbml
Project smart_workspace {
  database_type: "PostgreSQL"
  note: "Smart Workspace hybrid workspace/club management ERD for users, groups, leaders, attendance, Jira-style task tracking, collaboration, events, notifications, and files."
}

Enum membership_status {
  INVITED
  ACTIVE
  INACTIVE
  REMOVED
}

Enum project_status {
  ACTIVE
  ARCHIVED
  COMPLETED
}

Enum task_priority {
  LOW
  MEDIUM
  HIGH
  URGENT
}

Enum task_status {
  BACKLOG
  TODO
  IN_PROGRESS
  REVIEW
  DONE
  CANCELLED
}

Enum task_type {
  TASK
  BUG
  STORY
  EPIC
  IMPROVEMENT
}

Enum attendance_status {
  PENDING
  PRESENT
  ABSENT
  EXCUSED
  LATE
}

Enum attendee_status {
  PENDING
  ACCEPTED
  DECLINED
  ATTENDED
  ABSENT
}

Enum notification_type {
  TASK_ASSIGNED
  TASK_STATUS_CHANGED
  COMMENT_MENTION
  EVENT_UPDATED
  DEADLINE_REMINDER
  WORKSPACE_INVITE
  SYSTEM
}

Enum attachment_target_type {
  PROJECT
  TASK
  COMMENT
  EVENT
  ATTENDANCE_SESSION
}

Table users {
  id uuid [pk, note: "uuid v7" ]
  email varchar(255) [not null, unique]
  password_hash varchar(255) [not null]
  first_name varchar(255) [not null]
  last_name varchar(255)
  avatar_url varchar(1024)
  created_at timestamptz
  updated_at timestamptz
}

Table refresh_tokens {
  id uuid [pk, note: "uuid v7"]
  token varchar(512) [not null, unique]
  user_id uuid [not null, ref: > users.id]
  expiry_date timestamptz [not null]
  revoked boolean [not null, default: false]
  created_at timestamptz [not null]

  indexes {
    user_id
    token [unique]
  }
}

Table workspace_types {
  id uuid [pk, note: "uuid v7"]
  code varchar(50) [not null, unique, note: "Stable machine code, e.g. workspace, club, organization, team, community."]
  name varchar(100) [not null]
  description text
  is_active boolean [not null, default: true]
  created_at timestamptz
  updated_at timestamptz

  indexes {
    code [unique]
    is_active
  }
}

Table workspaces {
  id uuid [pk]
  name varchar(255) [not null]
  slug varchar(255) [not null, unique]
  type_id uuid [not null, ref: > workspace_types.id, note: "Configurable workspace type such as workspace, club, organization, team, or community."]
  description text
  created_by uuid [ref: > users.id]
  created_at timestamptz
  updated_at timestamptz

  indexes {
    slug [unique]
    type_id
    created_by
  }
}

Table roles {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  name varchar(100) [not null]
  permissions text [not null, note: "Legacy denormalized permission list kept for compatibility. Prefer role_permissions for normalized RBAC."]
  created_at timestamptz

  indexes {
    (workspace_id, name) [unique, name: "unique_workspace_role"]
  }
}

Table permissions {
  id uuid [pk]
  name varchar(150) [not null, unique, note: "Format: resource:action, e.g. task:create"]
}

Table role_permissions {
  role_id uuid [not null, ref: > roles.id]
  permission_id uuid [not null, ref: > permissions.id]

  indexes {
    (role_id, permission_id) [pk]
    permission_id
  }
}

Table workspace_members {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  user_id uuid [not null, ref: > users.id]
  role_id uuid [ref: > roles.id, note: "Nullable in database to preserve user membership if a role is deleted. Application must ensure every workspace has at least one owner."]
  status membership_status [not null, default: "ACTIVE"]
  joined_at timestamptz

  indexes {
    (workspace_id, user_id) [unique, name: "unique_workspace_user"]
    role_id
    (workspace_id, status)
  }
}

Table groups {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  name varchar(255) [not null]
  slug varchar(255) [not null]
  description text
  is_active boolean [not null, default: true]
  created_by uuid [ref: > users.id]
  created_at timestamptz
  updated_at timestamptz

  indexes {
    workspace_id
    (workspace_id, slug) [unique, name: "unique_workspace_group_slug"]
    (workspace_id, is_active)
  }
}

Table group_members {
  id uuid [pk]
  group_id uuid [not null, ref: > groups.id]
  workspace_member_id uuid [not null, ref: > workspace_members.id, note: "References a workspace membership so group membership cannot point outside the workspace."]
  is_leader boolean [not null, default: false]
  joined_at timestamptz
  left_at timestamptz

  indexes {
    (group_id, workspace_member_id) [unique, name: "unique_group_member"]
    (group_id, is_leader)
    workspace_member_id
  }
}

Table club_leads {
  workspace_id uuid [not null, ref: > workspaces.id]
  workspace_member_id uuid [not null, ref: > workspace_members.id, note: "Club/workspace lead. Application must ensure at least one active lead for active club workspaces."]
  assigned_by uuid [ref: > users.id]
  assigned_at timestamptz [not null]

  indexes {
    (workspace_id, workspace_member_id) [pk]
    workspace_member_id
    assigned_by
  }
}

Table workspace_invitations {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  invited_email varchar(255) [not null]
  invited_by uuid [not null, ref: > users.id]
  role_id uuid [ref: > roles.id]
  token varchar(512) [not null, unique]
  accepted_at timestamptz
  expires_at timestamptz [not null]
  created_at timestamptz [not null]

  indexes {
    workspace_id
    invited_email
    token [unique]
  }
}

Table projects {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  group_id uuid [ref: > groups.id, note: "Optional owning group/department for club-style work."]
  name varchar(255) [not null]
  description text
  status project_status [not null, default: "ACTIVE"]
  created_at timestamptz
  updated_at timestamptz

  indexes {
    workspace_id
    group_id
    (workspace_id, status)
  }
}

Table project_members {
  project_id uuid [not null, ref: > projects.id]
  user_id uuid [not null, ref: > users.id, note: "Must also be a member of the project workspace. Enforced by application/service validation."]
  role_name varchar(100)
  joined_at timestamptz

  indexes {
    (project_id, user_id) [pk]
    user_id
  }
}

Table boards {
  id uuid [pk]
  project_id uuid [not null, ref: > projects.id]
  name varchar(255) [not null]
  created_at timestamptz

  indexes {
    project_id
  }
}

Table board_columns {
  id uuid [pk]
  board_id uuid [not null, ref: > boards.id]
  name varchar(100) [not null]
  position int [not null]
  created_at timestamptz

  indexes {
    (board_id, position) [unique, name: "unique_board_column_position"]
  }
}

Table tasks {
  id uuid [pk]
  issue_key varchar(50) [not null, unique, note: "Human-readable Jira-style key, e.g. SW-123."]
  project_id uuid [not null, ref: > projects.id]
  column_id uuid [not null, ref: > board_columns.id]
  parent_task_id uuid [ref: > tasks.id, note: "Parent issue for subtasks or hierarchy."]
  type task_type [not null, default: "TASK"]
  status task_status [not null, default: "TODO", note: "Convenience status. Board column is the Kanban source of truth; service layer must keep status and column movement consistent."]
  title varchar(255) [not null]
  description text
  priority task_priority [not null, default: "MEDIUM"]
  due_date timestamptz
  created_by uuid [ref: > users.id]
  reporter_id uuid [ref: > users.id, note: "Jira-style reporter. Defaults to created_by when not specified."]
  estimated_points numeric(6,2)
  started_at timestamptz
  completed_at timestamptz
  created_at timestamptz
  updated_at timestamptz

  indexes {
    issue_key [unique]
    project_id
    column_id
    parent_task_id
    created_by
    reporter_id
    due_date
    status
    type
    (project_id, priority)
    (project_id, status)
  }
}

Table task_watchers {
  task_id uuid [not null, ref: > tasks.id]
  user_id uuid [not null, ref: > users.id, note: "Must be a member of the task project workspace. Enforced by application/service validation."]
  created_at timestamptz

  indexes {
    (task_id, user_id) [pk]
    user_id
  }
}

Table task_labels {
  id uuid [pk]
  project_id uuid [not null, ref: > projects.id]
  name varchar(100) [not null]
  color varchar(20)
  created_at timestamptz

  indexes {
    (project_id, name) [unique, name: "unique_project_label"]
  }
}

Table task_label_assignments {
  task_id uuid [not null, ref: > tasks.id]
  label_id uuid [not null, ref: > task_labels.id]

  indexes {
    (task_id, label_id) [pk]
    label_id
  }
}

Table task_assignees {
  task_id uuid [not null, ref: > tasks.id]
  user_id uuid [not null, ref: > users.id, note: "Must be a member of the task project workspace. Enforced by application/service validation."]
  assigned_at timestamptz

  indexes {
    (task_id, user_id) [pk]
    user_id
  }
}

Table task_dependencies {
  task_id uuid [not null, ref: > tasks.id, note: "Task blocked by dependency_task_id"]
  dependency_task_id uuid [not null, ref: > tasks.id]
  created_at timestamptz

  indexes {
    (task_id, dependency_task_id) [pk]
    dependency_task_id
  }
}

Table subtasks {
  id uuid [pk]
  task_id uuid [not null, ref: > tasks.id]
  title varchar(255) [not null]
  is_completed boolean [not null, default: false]
  position int [not null]
  created_at timestamptz

  indexes {
    task_id
    (task_id, position)
  }
}

Table comments {
  id uuid [pk]
  task_id uuid [not null, ref: > tasks.id]
  user_id uuid [not null, ref: > users.id, note: "Must be a member of the task project workspace. Enforced by application/service validation."]
  content text [not null]
  created_at timestamptz
  updated_at timestamptz

  indexes {
    task_id
    user_id
  }
}

Table comment_mentions {
  comment_id uuid [not null, ref: > comments.id]
  mentioned_user_id uuid [not null, ref: > users.id]

  indexes {
    (comment_id, mentioned_user_id) [pk]
    mentioned_user_id
  }
}

Table activity_logs {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  user_id uuid [ref: > users.id]
  action varchar(150) [not null]
  entity_type varchar(100) [not null]
  entity_id uuid
  details text
  created_at timestamptz

  indexes {
    workspace_id
    user_id
    (entity_type, entity_id)
    created_at
  }
}

Table events {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  group_id uuid [ref: > groups.id, note: "Optional group-specific event or meeting."]
  title varchar(255) [not null]
  description text
  start_time timestamptz [not null]
  end_time timestamptz [not null]
  location varchar(255)
  organizer_id uuid [ref: > users.id]
  created_at timestamptz

  indexes {
    workspace_id
    group_id
    organizer_id
    start_time
  }
}

Table event_attendees {
  event_id uuid [not null, ref: > events.id]
  user_id uuid [not null, ref: > users.id, note: "Must be a member of the event workspace. Enforced by application/service validation."]
  status attendee_status [not null, default: "PENDING"]

  indexes {
    (event_id, user_id) [pk]
    user_id
  }
}

Table attendance_sessions {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  event_id uuid [ref: > events.id, note: "Optional event/meeting connected to this attendance session."]
  group_id uuid [ref: > groups.id, note: "Optional group scope. If set, attendance should normally include members of this group."]
  title varchar(255) [not null]
  session_date date [not null]
  starts_at timestamptz
  ends_at timestamptz
  created_by uuid [not null, ref: > users.id]
  locked_at timestamptz
  created_at timestamptz [not null]
  updated_at timestamptz

  indexes {
    workspace_id
    event_id
    group_id
    session_date
  }
}

Table attendance_records {
  id uuid [pk]
  session_id uuid [not null, ref: > attendance_sessions.id]
  workspace_member_id uuid [not null, ref: > workspace_members.id, note: "Attendance is recorded for a workspace member, not just a user."]
  status attendance_status [not null, default: "PENDING"]
  marked_by uuid [ref: > users.id, note: "User who marked attendance. Must be authorized: club lead, admin, organizer, or group leader in scope."]
  marked_at timestamptz
  note text
  created_at timestamptz [not null]
  updated_at timestamptz

  indexes {
    (session_id, workspace_member_id) [unique, name: "unique_attendance_session_member"]
    workspace_member_id
    status
    marked_by
  }
}

Table notifications {
  id uuid [pk]
  user_id uuid [not null, ref: > users.id]
  title varchar(255) [not null]
  content text [not null]
  is_read boolean [not null, default: false]
  type notification_type [not null]
  link varchar(1024)
  created_at timestamptz

  indexes {
    user_id
    (user_id, is_read)
    created_at
  }
}

Table files {
  id uuid [pk]
  workspace_id uuid [not null, ref: > workspaces.id]
  uploaded_by uuid [not null, ref: > users.id]
  file_name varchar(255) [not null]
  content_type varchar(150)
  size_bytes bigint [not null]
  storage_provider varchar(50) [not null, default: "CLOUDINARY"]
  storage_key varchar(512) [not null]
  url varchar(1024) [not null]
  created_at timestamptz [not null]

  indexes {
    workspace_id
    uploaded_by
  }
}

Table attachments {
  id uuid [pk]
  file_id uuid [not null, ref: > files.id]
  target_type attachment_target_type [not null, note: "Polymorphic target. Database cannot enforce the target FK directly."]
  target_id uuid [not null, note: "References projects, tasks, comments, events, or attendance sessions depending on target_type."]
  attached_by uuid [not null, ref: > users.id]
  created_at timestamptz [not null]

  indexes {
    file_id
    (target_type, target_id)
    attached_by
  }
}

```
