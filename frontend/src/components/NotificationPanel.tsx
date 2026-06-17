import React, { useState, useEffect } from 'react';
import { Bell, Check } from 'lucide-react';
import { api } from '../services/api';

interface Notification {
  id: string;
  title: string;
  content: string;
  isRead: boolean;
  type: string;
  link: string | null;
  createdAt: string;
}

const NotificationPanel: React.FC = () => {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [showPanel, setShowPanel] = useState(false);

  const fetchNotifications = async () => {
    try {
      const list = await api.notification.list();
      setNotifications(list);
    } catch (e) {
      console.error('Failed to load notifications', e);
    }
  };

  useEffect(() => {
    fetchNotifications();
    const interval = setInterval(fetchNotifications, 15000);
    return () => clearInterval(interval);
  }, []);

  const handleRead = async (id: string) => {
    try {
      await api.notification.read(id);
      setNotifications(prev => prev.map(n => n.id === id ? { ...n, isRead: true } : n));
    } catch (e) {
      console.error(e);
    }
  };

  const handleReadAll = async () => {
    try {
      await api.notification.readAll();
      setNotifications(prev => prev.map(n => ({ ...n, isRead: true })));
    } catch (e) {
      console.error(e);
    }
  };

  const unreadCount = notifications.filter(n => !n.isRead).length;

  return (
    <div className="relative">
      <button
        onClick={() => setShowPanel(!showPanel)}
        className="p-2 rounded-full hover:bg-slate-800/60 text-slate-400 hover:text-slate-200 transition-all relative"
      >
        <Bell className="w-5 h-5" />
        {unreadCount > 0 && (
          <span className="absolute top-1 right-1 w-4 h-4 bg-indigo-500 rounded-full flex items-center justify-center text-[10px] font-bold text-white shadow shadow-indigo-500/50">
            {unreadCount}
          </span>
        )}
      </button>

      {showPanel && (
        <>
          <div className="fixed inset-0 z-30" onClick={() => setShowPanel(false)} />
          <div className="absolute right-0 mt-2 w-80 glass-panel border border-slate-800/85 rounded-xl shadow-2xl overflow-hidden z-40 animate-fade-in">
            <div className="p-4 border-b border-slate-800/40 flex items-center justify-between">
              <h3 className="font-semibold text-slate-200 text-sm">Notifications</h3>
              {unreadCount > 0 && (
                <button
                  onClick={handleReadAll}
                  className="flex items-center text-xs text-indigo-400 hover:text-indigo-300 font-medium transition-colors cursor-pointer"
                >
                  <Check className="w-3.5 h-3.5 mr-1" />
                  Mark all read
                </button>
              )}
            </div>

            <div className="max-h-72 overflow-y-auto divide-y divide-slate-800/30">
              {notifications.length === 0 ? (
                <div className="p-6 text-center text-xs text-slate-500">
                  No notifications yet.
                </div>
              ) : (
                notifications.map(n => (
                  <div 
                    key={n.id} 
                    className={`p-4 transition-colors ${n.isRead ? 'opacity-60 hover:opacity-100 hover:bg-slate-900/10' : 'bg-indigo-500/5 hover:bg-indigo-500/10'}`}
                  >
                    <div className="flex justify-between items-start mb-1">
                      <h4 className="text-xs font-bold text-slate-200">{n.title}</h4>
                      {!n.isRead && (
                        <button
                          onClick={() => handleRead(n.id)}
                          className="p-0.5 rounded text-indigo-400 hover:bg-indigo-500/10 cursor-pointer"
                          title="Mark read"
                        >
                          <Check className="w-3.5 h-3.5" />
                        </button>
                      )}
                    </div>
                    <p className="text-xs text-slate-400 mb-2 leading-relaxed">{n.content}</p>
                    <span className="text-[10px] text-slate-500">
                      {new Date(n.createdAt).toLocaleDateString()}
                    </span>
                  </div>
                ))
              )}
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default NotificationPanel;
