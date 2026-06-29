-- 1. Users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    online_status BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_users_email_phone ON users(email, phone);

-- 2. User Profiles table
CREATE TABLE user_profiles (
    user_id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    avatar_url VARCHAR(1024),
    bio TEXT,
    discord_id VARCHAR(50) UNIQUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- 3. Workspaces table
CREATE TABLE workspaces (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    owner_id UUID NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- 4. Workspace Members table
CREATE TABLE workspace_members (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    joined_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_workspace_member UNIQUE (workspace_id, user_id)
);

-- 5. Roles table
CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_workspace_role_name UNIQUE (workspace_id, name)
);

-- 6. Permissions table
CREATE TABLE permissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(100) NOT NULL UNIQUE
);

-- 7. Role Permissions join table
CREATE TABLE role_permissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    CONSTRAINT uk_role_permission UNIQUE (role_id, permission_id)
);

-- 8. Workspace Member Roles join table
CREATE TABLE workspace_member_roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_member_id UUID NOT NULL REFERENCES workspace_members(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT uk_workspace_member_role UNIQUE (workspace_member_id, role_id)
);

-- 9. Teams table
CREATE TABLE teams (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_workspace_team_name UNIQUE (workspace_id, name)
);

-- 10. Team Members table
CREATE TABLE team_members (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    team_id UUID NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    workspace_member_id UUID NOT NULL REFERENCES workspace_members(id) ON DELETE CASCADE,
    joined_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_team_member UNIQUE (team_id, workspace_member_id)
);

-- 11. Workspace Meeting Schedules table
CREATE TABLE workspace_meeting_schedules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_workspace_meeting_title UNIQUE (workspace_id, title)
);

-- 12. Workspace Member Warnings table
CREATE TABLE workspace_member_warnings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_member_id UUID NOT NULL REFERENCES workspace_members(id) ON DELETE CASCADE,
    warning_type VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_workspace_member_warning_type ON workspace_member_warnings(workspace_member_id, warning_type);

-- 13. Assignments table
CREATE TABLE assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_id UUID NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    deadline TIMESTAMPTZ NOT NULL,
    created_by UUID NOT NULL REFERENCES workspace_members(id) ON DELETE RESTRICT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_workspace_assignment_title UNIQUE (workspace_id, title)
);

-- 14. Assignment Assignees table
CREATE TABLE assignment_assignees (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    assignment_id UUID NOT NULL REFERENCES assignments(id) ON DELETE CASCADE,
    workspace_member_id UUID NOT NULL REFERENCES workspace_members(id) ON DELETE CASCADE,
    assigned_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_assignment_assignee UNIQUE (assignment_id, workspace_member_id)
);

-- 15. Assignment Submissions table
CREATE TABLE assignment_submissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    assignment_assignee_id UUID NOT NULL REFERENCES assignment_assignees(id) ON DELETE CASCADE,
    submission_url VARCHAR(1024) NOT NULL,
    submitted_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_assignment_submission_assignee ON assignment_submissions(assignment_assignee_id);

-- 16. Workspace Member Letters table
CREATE TABLE workspace_member_letters (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workspace_member_id UUID NOT NULL REFERENCES workspace_members(id) ON DELETE CASCADE,
    letter_type VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'pending',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_workspace_member_letter_type ON workspace_member_letters(workspace_member_id, letter_type);

-- 17. Absence Letters table
CREATE TABLE workspace_member_absence_letters (
    letter_id UUID PRIMARY KEY REFERENCES workspace_member_letters(id) ON DELETE CASCADE,
    workspace_meeting_schedule_id UUID NOT NULL REFERENCES workspace_meeting_schedules(id) ON DELETE CASCADE,
    absence_date DATE NOT NULL
);
CREATE INDEX idx_workspace_member_absence_letter_schedule_date ON workspace_member_absence_letters(workspace_meeting_schedule_id, absence_date);

-- 18. Late Letters table
CREATE TABLE workspace_member_late_letters (
    letter_id UUID PRIMARY KEY REFERENCES workspace_member_letters(id) ON DELETE CASCADE,
    workspace_meeting_schedule_id UUID NOT NULL REFERENCES workspace_meeting_schedules(id) ON DELETE CASCADE,
    late_date DATE NOT NULL,
    expected_arrival_time TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_workspace_member_late_letter_schedule_date ON workspace_member_late_letters(workspace_meeting_schedule_id, late_date);

-- 19. Assignment Postpone Letters table
CREATE TABLE workspace_member_assignment_postpone_letters (
    letter_id UUID PRIMARY KEY REFERENCES workspace_member_letters(id) ON DELETE CASCADE,
    assignment_id UUID NOT NULL REFERENCES assignments(id) ON DELETE CASCADE,
    assignment_snapshot VARCHAR(255),
    old_deadline TIMESTAMPTZ NOT NULL,
    requested_deadline TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_postpone_letter_assignment_deadlines ON workspace_member_assignment_postpone_letters(assignment_id, old_deadline, requested_deadline);

-- 20. Refresh Tokens table
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- 21. Cross-table Workspace Validation Triggers

-- Validate workspace_member_roles: member and role must be in the same workspace
CREATE OR REPLACE FUNCTION validate_workspace_member_role()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT workspace_id FROM workspace_members WHERE id = NEW.workspace_member_id) !=
       (SELECT workspace_id FROM roles WHERE id = NEW.role_id) THEN
        RAISE EXCEPTION 'Workspace Member and Role must belong to the same workspace';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_workspace_member_role
BEFORE INSERT OR UPDATE ON workspace_member_roles
FOR EACH ROW EXECUTE FUNCTION validate_workspace_member_role();

-- Validate team_members: team and member must be in the same workspace
CREATE OR REPLACE FUNCTION validate_team_member()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT workspace_id FROM teams WHERE id = NEW.team_id) !=
       (SELECT workspace_id FROM workspace_members WHERE id = NEW.workspace_member_id) THEN
        RAISE EXCEPTION 'Team and Workspace Member must belong to the same workspace';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_team_member
BEFORE INSERT OR UPDATE ON team_members
FOR EACH ROW EXECUTE FUNCTION validate_team_member();

-- Validate assignments creator: creator member must be in the same workspace as the assignment
CREATE OR REPLACE FUNCTION validate_assignment_creator()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.workspace_id != (SELECT workspace_id FROM workspace_members WHERE id = NEW.created_by) THEN
        RAISE EXCEPTION 'Assignment creator must belong to the same workspace as the assignment';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_assignment_creator
BEFORE INSERT OR UPDATE ON assignments
FOR EACH ROW EXECUTE FUNCTION validate_assignment_creator();

-- Validate assignment_assignees: assignee member must be in the same workspace as the assignment
CREATE OR REPLACE FUNCTION validate_assignment_assignee()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT workspace_id FROM assignments WHERE id = NEW.assignment_id) !=
       (SELECT workspace_id FROM workspace_members WHERE id = NEW.workspace_member_id) THEN
        RAISE EXCEPTION 'Assignment and Assignee must belong to the same workspace';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_assignment_assignee
BEFORE INSERT OR UPDATE ON assignment_assignees
FOR EACH ROW EXECUTE FUNCTION validate_assignment_assignee();

-- Validate workspace_member_absence_letters: letter member and meeting schedule must be in the same workspace
CREATE OR REPLACE FUNCTION validate_absence_letter()
RETURNS TRIGGER AS $$
DECLARE
    letter_workspace_id UUID;
    meeting_workspace_id UUID;
BEGIN
    SELECT m.workspace_id INTO letter_workspace_id 
    FROM workspace_member_letters l
    JOIN workspace_members m ON l.workspace_member_id = m.id
    WHERE l.id = NEW.letter_id;

    SELECT workspace_id INTO meeting_workspace_id
    FROM workspace_meeting_schedules
    WHERE id = NEW.workspace_meeting_schedule_id;

    IF letter_workspace_id != meeting_workspace_id THEN
        RAISE EXCEPTION 'Absence letter and meeting schedule must belong to the same workspace';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_absence_letter
BEFORE INSERT OR UPDATE ON workspace_member_absence_letters
FOR EACH ROW EXECUTE FUNCTION validate_absence_letter();

-- Validate workspace_member_late_letters: letter member and meeting schedule must be in the same workspace
CREATE OR REPLACE FUNCTION validate_late_letter()
RETURNS TRIGGER AS $$
DECLARE
    letter_workspace_id UUID;
    meeting_workspace_id UUID;
BEGIN
    SELECT m.workspace_id INTO letter_workspace_id 
    FROM workspace_member_letters l
    JOIN workspace_members m ON l.workspace_member_id = m.id
    WHERE l.id = NEW.letter_id;

    SELECT workspace_id INTO meeting_workspace_id
    FROM workspace_meeting_schedules
    WHERE id = NEW.workspace_meeting_schedule_id;

    IF letter_workspace_id != meeting_workspace_id THEN
        RAISE EXCEPTION 'Late letter and meeting schedule must belong to the same workspace';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_late_letter
BEFORE INSERT OR UPDATE ON workspace_member_late_letters
FOR EACH ROW EXECUTE FUNCTION validate_late_letter();

-- Validate workspace_member_assignment_postpone_letters: letter member and assignment must be in the same workspace
CREATE OR REPLACE FUNCTION validate_postpone_letter()
RETURNS TRIGGER AS $$
DECLARE
    letter_workspace_id UUID;
    assignment_workspace_id UUID;
BEGIN
    SELECT m.workspace_id INTO letter_workspace_id 
    FROM workspace_member_letters l
    JOIN workspace_members m ON l.workspace_member_id = m.id
    WHERE l.id = NEW.letter_id;

    SELECT workspace_id INTO assignment_workspace_id
    FROM assignments
    WHERE id = NEW.assignment_id;

    IF letter_workspace_id != assignment_workspace_id THEN
        RAISE EXCEPTION 'Postpone letter and assignment must belong to the same workspace';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_postpone_letter
BEFORE INSERT OR UPDATE ON workspace_member_assignment_postpone_letters
FOR EACH ROW EXECUTE FUNCTION validate_postpone_letter();
