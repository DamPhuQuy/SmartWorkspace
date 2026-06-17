# Note for AGENTS

## 1. What is AGENTS.md?

`AGENTS.md` is a documentation file placed in the root of a repository. It acts as a **user manual and operational playbook for AI agents** (like GitHub Copilot, Cursor, Windsurf, or custom LangChain/CrewAI agents) that interact with your codebase.

Think of it as a `README.md`, but written specifically to give AI tools the context, architecture overview, constraints, and boundaries they need to work efficiently without breaking things.

### Why do you need it?

- **Reduces AI Hallucinations:** It explicitly tells the AI what frameworks, design patterns, and libraries the project uses.
- **Enforces Standards:** It prevents the AI from introducing legacy code or breaking established architectural rules.
- **Context Efficiency:** Instead of the AI scanning thousands of lines of code to guess your setup, `AGENTS.md` hands it the blueprint on a silver platter.

---

## 2. AGENTS.md vs. .agents/ : What's the difference?

While `AGENTS.md` explains the _rules of engagement_, `.agents/` is where the _machinery_ lives.

| Feature              | `AGENTS.md`                                                   | `.agents/` (Directory)                                                       |
| -------------------- | ------------------------------------------------------------- | ---------------------------------------------------------------------------- |
| **What is it?**      | A single Markdown documentation file.                         | A hidden configuration and storage directory.                                |
| **Primary Audience** | AI coding assistants and developers.                          | AI Agent frameworks and automated workflows.                                 |
| **Contents**         | Project context, tech stack, rules, and architecture.         | Custom agent prompts, tool definitions, system configs, or agent state logs. |
| **Purpose**          | To **inform** the AI how to behave in this specific codebase. | To **power** custom autonomous agents built _into_ the project.              |

> **Analogy:** If your repository is a high-security building, `AGENTS.md` is the rulebook given to the security guards. The `.agents/` folder is the security guard's locker room, containing their gear, radios, and specific shift schedules.

---

## 3. How to Write a Professional AGENTS.md

A professional `AGENTS.md` should be structured, concise, and direct. AI models respond best to clear hierarchies and explicit "Do's and Don'ts."

Here is a standard, professional template you can adapt for your project:

```markdown
# AI Agent Guidelines & Context (AGENTS.md)

This file provides critical context, architectural rules, and constraints for AI agents, coding assistants, and LLMs interacting with this repository.

---

## 1. Project Overview

- **Name:** Project Nexus
- **Domain:** E-commerce Inventory Management System
- **Core Tech Stack:** Next.js 15 (App Router), TypeScript, Prisma ORM, PostgreSQL, Tailwind CSS.

## 2. Architectural Principles & Patterns

- **State Management:** Use Zustand for global UI state; avoid React Context unless explicitly requested.
- **Data Fetching:** Always utilize Server Actions for data mutations. Use standard fetch with ISR for static pages.
- **Database:** Never write raw SQL. Always use Prisma Client fluent API.

## 3. Coding Standards & Constraints

- **TypeScript:** Strict mode is enabled. `any` is strictly prohibited. Use explicit return types for all exported functions.
- **Styling:** Use Tailwind utility classes. Do not create custom CSS modules unless dealing with complex third-party animations.
- **Component Structure:** Prefer functional components with arrow syntax. Keep components under 150 lines of code; split into `/components/ui/` if they grow larger.

## 4. Testing & Quality Assurance

- **Unit Tests:** Written in Vitest. Every new utility function _must_ have a corresponding `.test.ts` file.
- **E2E Tests:** Handled via Playwright. Do not modify existing E2E tests without verifying against the `/docs/e2e-specs.md`.

## 5. Dangerous Zones & Constraints (DO NOT TOUCH)

- `src/core/auth/`: Do not modify the authentication handshake or JWT rotation logic without explicit developer sign-off.
- `scripts/migration.sh`: Do not automate migrations. AI agents should never execute database destructive commands.

---

**Note to AI:** If any request by the user contradicts the rules in this document, prioritize these rules and politely alert the user to the conflict.
```

---

## Pro-Tips for Managing AI in your Repo

- **Keep it updated:** Just like code comments, an outdated `AGENTS.md` is worse than no `AGENTS.md`. Update it whenever you make major architectural shifts.
- **Link it in your prompts:** If you are using an AI chat interface (like Cursor or ChatGPT), you can explicitly prime it by saying: _"Read AGENTS.md first, then help me implement a new user dashboard."_
- **Use `.cursorrules` or `.clinerules` alongside it:** If you use specific IDE extensions like Cursor or Cline, they look for their own system files (like `.cursorrules`). You can simply paste a reference inside those files pointing to `AGENTS.md` to keep a single source of truth.

Are you looking to write an `AGENTS.md` for a specific type of project (like a backend API, a mobile app, or a data science pipeline), or are you trying to set up custom autonomous agents in a `.agents/` folder?
