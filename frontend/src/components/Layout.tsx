import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { 
  LayoutDashboard, 
  Briefcase, 
  Calendar, 
  Settings, 
  LogOut, 
  Plus,
  ChevronDown,
  User
} from 'lucide-react';
import NotificationPanel from './NotificationPanel';

export const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user, logout, workspaces, currentWorkspace, selectWorkspace, refreshWorkspaces } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  
  const [showWsMenu, setShowWsMenu] = useState(false);
  const [showCreateWsModal, setShowCreateWsModal] = useState(false);
  const [newWsName, setNewWsName] = useState('');
  const [newWsSlug, setNewWsSlug] = useState('');
  const [newWsDesc, setNewWsDesc] = useState('');
  const [error, setError] = useState('');

  const navItems = [
    { name: 'Dashboard', path: '/', icon: LayoutDashboard },
    { name: 'Projects', path: '/projects', icon: Briefcase },
    { name: 'Events', path: '/events', icon: Calendar },
    { name: 'Settings & Members', path: '/settings', icon: Settings },
  ];

  const handleCreateWorkspace = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    if (!newWsName.trim() || !newWsSlug.trim()) {
      setError('Name and slug are required');
      return;
    }
    try {
      const response = await fetch('http://localhost:8080/api/workspaces', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('auth_token')}`
        },
        body: JSON.stringify({
          name: newWsName,
          slug: newWsSlug.toLowerCase().replace(/[^a-z0-9-_]/g, '-'),
          description: newWsDesc
        })
      });
      if (!response.ok) {
        throw new Error(await response.text() || 'Failed to create workspace');
      }
      await refreshWorkspaces();
      setShowCreateWsModal(false);
      setNewWsName('');
      setNewWsSlug('');
      setNewWsDesc('');
    } catch (err: any) {
      setError(err.message || 'Something went wrong');
    }
  };

  return (
    <div className="flex h-screen bg-[#f6f5f4] text-slate-800 overflow-hidden font-sans">
      {/* Sidebar */}
      <aside className="w-64 bg-[#f6f5f4] border-r border-slate-200 flex flex-col z-20">
        {/* Workspace Switcher */}
        <div className="p-4 border-b border-slate-200/60 relative">
          <button 
            onClick={() => setShowWsMenu(!showWsMenu)}
            className="w-full flex items-center justify-between p-2 rounded-lg bg-white hover:bg-slate-100 border border-slate-200/80 transition-all text-left group cursor-pointer"
          >
            <div className="flex items-center min-w-0">
              <div className="w-8 h-8 rounded bg-indigo-600 flex items-center justify-center font-bold text-white text-sm shrink-0 mr-2 shadow-sm">
                {currentWorkspace ? currentWorkspace.name.substring(0, 2).toUpperCase() : 'SW'}
              </div>
              <div className="min-w-0">
                <p className="text-sm font-semibold text-slate-800 truncate group-hover:text-indigo-600 transition-colors">
                  {currentWorkspace ? currentWorkspace.name : 'No Workspace'}
                </p>
                <p className="text-xs text-slate-500 truncate">
                  {currentWorkspace ? `@${currentWorkspace.slug}` : 'Select workspace'}
                </p>
              </div>
            </div>
            <ChevronDown className="w-4 h-4 text-slate-500 group-hover:text-slate-700" />
          </button>

          {/* Workspace Dropdown */}
          {showWsMenu && (
            <div className="absolute top-full left-4 right-4 mt-1 bg-white border border-slate-200 rounded-lg shadow-xl p-1 z-30 animate-fade-in">
              <div className="max-h-48 overflow-y-auto">
                {workspaces.map(ws => (
                  <button
                    key={ws.id}
                    onClick={() => {
                      selectWorkspace(ws);
                      setShowWsMenu(false);
                      navigate('/');
                    }}
                    className={`w-full flex items-center p-2 rounded text-left text-sm transition-colors cursor-pointer ${
                      currentWorkspace?.id === ws.id 
                        ? 'bg-indigo-500/10 text-indigo-600 font-medium' 
                        : 'hover:bg-slate-100 text-slate-650 hover:text-slate-800'
                    }`}
                  >
                    <div className="w-6 h-6 rounded bg-slate-200 flex items-center justify-center font-bold text-xs mr-2 shrink-0">
                      {ws.name.substring(0, 2).toUpperCase()}
                    </div>
                    <span className="truncate">{ws.name}</span>
                  </button>
                ))}
              </div>
              <div className="border-t border-slate-250 my-1 pt-1">
                <button
                  onClick={() => {
                    setShowWsMenu(false);
                    setShowCreateWsModal(true);
                  }}
                  className="w-full flex items-center p-2 rounded text-left text-xs text-indigo-600 hover:bg-slate-100 hover:text-indigo-750 transition-all font-medium cursor-pointer"
                >
                  <Plus className="w-4 h-4 mr-2" />
                  Create Workspace
                </button>
              </div>
            </div>
          )}
        </div>

        {/* Sidebar Nav */}
        <nav className="flex-1 px-3 py-4 space-y-1 overflow-y-auto">
          {navItems.map(item => {
            const Icon = item.icon;
            const isActive = location.pathname === item.path;
            return (
              <button
                key={item.name}
                onClick={() => navigate(item.path)}
                className={`w-full flex items-center px-3 py-2.5 rounded-lg text-sm transition-all group cursor-pointer ${
                  isActive 
                    ? 'bg-indigo-500/10 border-l-2 border-indigo-600 text-indigo-600 font-semibold' 
                    : 'text-slate-600 hover:bg-slate-200/50 hover:text-slate-900'
                }`}
              >
                <Icon className={`w-5 h-5 mr-3 transition-colors ${isActive ? 'text-indigo-600' : 'text-slate-500 group-hover:text-slate-700'}`} />
                {item.name}
              </button>
            );
          })}
        </nav>

        {/* Sidebar Footer / User Profile */}
        <div className="p-4 border-t border-slate-200/60 flex items-center justify-between">
          <div className="flex items-center min-w-0 mr-2">
            <div className="w-9 h-9 rounded-full bg-slate-200 border border-slate-300/60 flex items-center justify-center font-bold text-indigo-600 shrink-0 shadow-inner mr-2">
              {user ? user.fullName.substring(0, 2).toUpperCase() : <User className="w-4 h-4" />}
            </div>
            <div className="min-w-0">
              <p className="text-sm font-semibold text-slate-805 truncate">{user ? user.fullName : 'Guest'}</p>
              <p className="text-xs text-slate-500 truncate">{user ? user.email : ''}</p>
            </div>
          </div>
          <button 
            onClick={logout}
            className="p-1.5 rounded-md text-slate-400 hover:text-rose-600 hover:bg-rose-500/10 transition-all cursor-pointer"
            title="Log Out"
          >
            <LogOut className="w-5 h-5" />
          </button>
        </div>
      </aside>

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Top Header */}
        <header className="h-16 border-b border-slate-200 flex items-center justify-between px-8 bg-white shrink-0">
          <div className="flex items-center space-x-2">
            <h1 className="text-lg font-bold text-slate-800">
              Smart Workspace
            </h1>
            {currentWorkspace && (
              <span className="text-xs px-2.5 py-0.5 rounded-full bg-indigo-500/10 text-indigo-600 border border-indigo-500/20 font-medium">
                {currentWorkspace.name}
              </span>
            )}
          </div>
          <div className="flex items-center space-x-4">
            <NotificationPanel />
          </div>
        </header>

        {/* Dynamic Page Views */}
        <main className="flex-1 overflow-y-auto p-8 relative">
          {children}
        </main>
      </div>

      {/* Create Workspace Modal */}
      {showCreateWsModal && (
        <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-50 p-4 animate-fade-in">
          <div className="bg-white border border-slate-200 rounded-xl max-w-md w-full shadow-lg p-6 relative text-slate-800">
            <h3 className="text-lg font-bold text-slate-800 mb-4">
              Create New Workspace
            </h3>
            {error && (
              <div className="mb-4 text-xs bg-rose-500/10 border border-rose-500/20 text-rose-600 p-2.5 rounded-lg">
                {error}
              </div>
            )}
            <form onSubmit={handleCreateWorkspace} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold text-slate-500 uppercase tracking-wider mb-1.5">
                  Workspace Name
                </label>
                <input 
                  type="text" 
                  value={newWsName} 
                  onChange={(e) => {
                    setNewWsName(e.target.value);
                    setNewWsSlug(e.target.value.toLowerCase().replace(/[^a-z0-9-_]/g, '-'));
                  }}
                  placeholder="e.g. Acme Corporation, Tech Club"
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm"
                />
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-500 uppercase tracking-wider mb-1.5">
                  Workspace URL Slug
                </label>
                <div className="relative flex items-center">
                  <span className="absolute left-3 text-slate-400 text-sm">@</span>
                  <input 
                    type="text" 
                    value={newWsSlug} 
                    onChange={(e) => setNewWsSlug(e.target.value.toLowerCase().replace(/[^a-z0-9-_]/g, '-'))}
                    placeholder="acme-corp"
                    className="w-full glass-input pl-8 pr-3.5 py-2 rounded-lg text-sm"
                  />
                </div>
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-500 uppercase tracking-wider mb-1.5">
                  Description
                </label>
                <textarea 
                  value={newWsDesc} 
                  onChange={(e) => setNewWsDesc(e.target.value)}
                  placeholder="Tell us what this workspace is about..."
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm h-20 resize-none"
                />
              </div>
              <div className="flex justify-end space-x-3 pt-2">
                <button 
                  type="button" 
                  onClick={() => setShowCreateWsModal(false)}
                  className="px-4 py-2 rounded-lg text-sm font-medium text-slate-500 hover:text-slate-700 transition-colors cursor-pointer"
                >
                  Cancel
                </button>
                <button 
                  type="submit" 
                  className="px-4 py-2 rounded-lg text-sm font-semibold text-white bg-indigo-600 hover:bg-indigo-500 active:scale-95 transition-all cursor-pointer"
                >
                  Create
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
