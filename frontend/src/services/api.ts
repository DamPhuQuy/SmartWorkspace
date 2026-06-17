const BASE_URL = 'http://localhost:8080';

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const token = localStorage.getItem('auth_token');
  const headers = new Headers(options.headers || {});
  
  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }
  
  if (!(options.body instanceof FormData) && !headers.has('Content-Type')) {
    headers.set('Content-Type', 'application/json');
  }

  const response = await fetch(`${BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    if (response.status === 401) {
      localStorage.removeItem('auth_token');
      localStorage.removeItem('refresh_token');
      // Redirect to login if on client-side and not already there
      if (window.location.pathname !== '/login' && window.location.pathname !== '/register') {
        window.location.href = '/login';
      }
    }
    const errorText = await response.text();
    throw new Error(errorText || `Request failed with status ${response.status}`);
  }

  if (response.status === 204) {
    return {} as T;
  }

  return response.json() as Promise<T>;
}

export const api = {
  // Auth API
  auth: {
    register: (data: any) => request<any>('/api/auth/register', { method: 'POST', body: JSON.stringify(data) }),
    login: (data: any) => request<any>('/api/auth/login', { method: 'POST', body: JSON.stringify(data) }),
    refresh: (refreshToken: string) => request<any>('/api/auth/refresh', {
      method: 'POST',
      body: JSON.stringify({ refreshToken })
    }),
    logout: (refreshToken: string) => request<void>('/api/auth/logout', {
      method: 'POST',
      body: JSON.stringify({ refreshToken })
    }),
    me: () => request<any>('/api/auth/me'),
  },

  // Workspaces API
  workspace: {
    list: () => request<any[]>('/api/workspaces'),
    create: (data: any) => request<any>('/api/workspaces', { method: 'POST', body: JSON.stringify(data) }),
    getBySlug: (slug: string) => request<any>(`/api/workspaces/${slug}`),
    members: (workspaceId: string) => request<any[]>(`/api/workspaces/${workspaceId}/members`),
    invite: (workspaceId: string, data: any) => request<any>(`/api/workspaces/${workspaceId}/members/invite`, { method: 'POST', body: JSON.stringify(data) }),
    updateMember: (workspaceId: string, memberId: string, roleName: string) => 
      request<any>(`/api/workspaces/${workspaceId}/members/${memberId}?roleName=${roleName}`, { method: 'PUT' }),
    removeMember: (workspaceId: string, memberId: string) => 
      request<void>(`/api/workspaces/${workspaceId}/members/${memberId}`, { method: 'DELETE' }),
  },

  // Projects API
  project: {
    list: (workspaceId: string) => request<any[]>(`/api/workspaces/${workspaceId}/projects`),
    create: (workspaceId: string, data: any) => request<any>(`/api/workspaces/${workspaceId}/projects`, { method: 'POST', body: JSON.stringify(data) }),
    delete: (projectId: string) => request<void>(`/api/projects/${projectId}`, { method: 'DELETE' }),
  },

  // Tasks API
  task: {
    getBoard: (projectId: string) => request<any>(`/api/projects/${projectId}/board`),
    createColumn: (boardId: string, name: string) => request<any>(`/api/boards/${boardId}/columns?name=${encodeURIComponent(name)}`, { method: 'POST' }),
    createTask: (projectId: string, columnId: string, data: any) => 
      request<any>(`/api/projects/${projectId}/columns/${columnId}/tasks`, { method: 'POST', body: JSON.stringify(data) }),
    updateTask: (taskId: string, data: any) => request<any>(`/api/tasks/${taskId}`, { method: 'PUT', body: JSON.stringify(data) }),
    deleteTask: (taskId: string) => request<void>(`/api/tasks/${taskId}`, { method: 'DELETE' }),
    
    // Subtasks
    getSubtasks: (taskId: string) => request<any[]>(`/api/tasks/${taskId}/subtasks`),
    createSubtask: (taskId: string, data: any) => request<any>(`/api/tasks/${taskId}/subtasks`, { method: 'POST', body: JSON.stringify(data) }),
    toggleSubtask: (subtaskId: string, isCompleted: boolean) => 
      request<any>(`/api/subtasks/${subtaskId}?isCompleted=${isCompleted}`, { method: 'PUT' }),
    deleteSubtask: (subtaskId: string) => request<void>(`/api/subtasks/${subtaskId}`, { method: 'DELETE' }),
  },

  // Collaboration API
  collaboration: {
    comments: (taskId: string) => request<any[]>(`/api/tasks/${taskId}/comments`),
    addComment: (taskId: string, data: any) => request<any>(`/api/tasks/${taskId}/comments`, { method: 'POST', body: JSON.stringify(data) }),
    activityLogs: (workspaceId: string) => request<any[]>(`/api/workspaces/${workspaceId}/activity-logs`),
  },

  // Events API
  event: {
    list: (workspaceId: string) => request<any[]>(`/api/workspaces/${workspaceId}/events`),
    create: (workspaceId: string, data: any) => request<any>(`/api/workspaces/${workspaceId}/events`, { method: 'POST', body: JSON.stringify(data) }),
    attendees: (eventId: string) => request<any[]>(`/api/events/${eventId}/attendees`),
    rsvp: (eventId: string, status: string) => request<any>(`/api/events/${eventId}/rsvp?status=${status}`, { method: 'PUT' }),
    delete: (eventId: string) => request<void>(`/api/events/${eventId}`, { method: 'DELETE' }),
  },

  // Notifications API
  notification: {
    list: () => request<any[]>('/api/notifications'),
    read: (id: string) => request<any>(`/api/notifications/${id}/read`, { method: 'PUT' }),
    readAll: () => request<void>('/api/notifications/read-all', { method: 'PUT' }),
  },
};
