import React, { createContext, useContext, useState, useEffect } from 'react';
import { api } from '../services/api';

interface User {
  id: string;
  email: string;
  fullName: string;
  avatarUrl: string | null;
}

interface Workspace {
  id: string;
  name: string;
  slug: string;
  description: string;
}

interface AuthContextType {
  user: User | null;
  loading: boolean;
  workspaces: Workspace[];
  currentWorkspace: Workspace | null;
  login: (data: any) => Promise<void>;
  register: (data: any) => Promise<void>;
  logout: () => void;
  selectWorkspace: (workspace: Workspace) => void;
  refreshWorkspaces: () => Promise<Workspace[]>;
  setCurrentWorkspace: (workspace: Workspace | null) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [workspaces, setWorkspaces] = useState<Workspace[]>([]);
  const [currentWorkspace, setCurrentWorkspaceState] = useState<Workspace | null>(null);
  const [loading, setLoading] = useState(true);

  const selectWorkspace = (workspace: Workspace) => {
    setCurrentWorkspaceState(workspace);
    localStorage.setItem('current_workspace_id', workspace.id);
  };

  const setCurrentWorkspace = (workspace: Workspace | null) => {
    setCurrentWorkspaceState(workspace);
    if (workspace) {
      localStorage.setItem('current_workspace_id', workspace.id);
    } else {
      localStorage.removeItem('current_workspace_id');
    }
  };

  const loadUser = async () => {
    const token = localStorage.getItem('auth_token');
    if (!token) {
      setLoading(false);
      return;
    }
    try {
      const me = await api.auth.me();
      setUser(me);
      const wsList = await api.workspace.list();
      setWorkspaces(wsList);
      
      const storedWsId = localStorage.getItem('current_workspace_id');
      if (storedWsId) {
        const found = wsList.find(w => w.id === storedWsId);
        if (found) {
          setCurrentWorkspaceState(found);
        } else if (wsList.length > 0) {
          selectWorkspace(wsList[0]);
        }
      } else if (wsList.length > 0) {
        selectWorkspace(wsList[0]);
      }
    } catch (e) {
      console.error('Failed to load user session', e);
      logout();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadUser();
  }, []);

  const login = async (data: any) => {
    setLoading(true);
    try {
      const res = await api.auth.login(data);
      localStorage.setItem('auth_token', res.accessToken ?? res.token);
      if (res.refreshToken) {
        localStorage.setItem('refresh_token', res.refreshToken);
      }
      setUser(res.user);
      const wsList = await api.workspace.list();
      setWorkspaces(wsList);
      if (wsList.length > 0) {
        selectWorkspace(wsList[0]);
      }
    } finally {
      setLoading(false);
    }
  };

  const register = async (data: any) => {
    setLoading(true);
    try {
      const res = await api.auth.register(data);
      localStorage.setItem('auth_token', res.accessToken ?? res.token);
      if (res.refreshToken) {
        localStorage.setItem('refresh_token', res.refreshToken);
      }
      setUser(res.user);
      setWorkspaces([]);
      setCurrentWorkspaceState(null);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    const refreshToken = localStorage.getItem('refresh_token');
    if (refreshToken) {
      api.auth.logout(refreshToken).catch(() => undefined);
    }
    localStorage.removeItem('auth_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('current_workspace_id');
    setUser(null);
    setWorkspaces([]);
    setCurrentWorkspaceState(null);
  };

  const refreshWorkspaces = async () => {
    try {
      const wsList = await api.workspace.list();
      setWorkspaces(wsList);
      if (currentWorkspace) {
        const stillExists = wsList.find(w => w.id === currentWorkspace.id);
        if (!stillExists && wsList.length > 0) {
          selectWorkspace(wsList[0]);
        }
      } else if (wsList.length > 0) {
        selectWorkspace(wsList[0]);
      }
      return wsList;
    } catch (e) {
      console.error(e);
      return [];
    }
  };

  return (
    <AuthContext.Provider value={{
      user,
      loading,
      workspaces,
      currentWorkspace,
      login,
      register,
      logout,
      selectWorkspace,
      refreshWorkspaces,
      setCurrentWorkspace
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
