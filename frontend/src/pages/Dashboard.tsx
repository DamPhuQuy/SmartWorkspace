import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { api } from '../services/api';
import { 
  Briefcase, 
  CheckCircle, 
  Calendar, 
  Activity, 
  Plus, 
  Clock, 
  MapPin,
  TrendingUp
} from 'lucide-react';

const Dashboard: React.FC = () => {
  const { currentWorkspace, user } = useAuth();

  const [projectsCount, setProjectsCount] = useState(0);
  const [tasksCount, setTasksCount] = useState(0);
  const [completedTasksCount, setCompletedTasksCount] = useState(0);
  const [events, setEvents] = useState<any[]>([]);
  const [activities, setActivities] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Quick Create Modal States
  const [showProjectModal, setShowProjectModal] = useState(false);
  const [projectName, setProjectName] = useState('');
  const [projectDesc, setProjectDesc] = useState('');

  const [showEventModal, setShowEventModal] = useState(false);
  const [eventTitle, setEventTitle] = useState('');
  const [eventDesc, setEventDesc] = useState('');
  const [eventStart, setEventStart] = useState('');
  const [eventEnd, setEventEnd] = useState('');
  const [eventLocation, setEventLocation] = useState('');

  const fetchData = async () => {
    if (!currentWorkspace) {
      setLoading(false);
      return;
    }
    setLoading(true);
    try {
      // Fetch projects
      const projs = await api.project.list(currentWorkspace.id);
      setProjectsCount(projs.length);

      // Fetch tasks count and completed tasks from all project boards
      let totalTasks = 0;
      let completedTasks = 0;
      for (const p of projs) {
        try {
          const board = await api.task.getBoard(p.id);
          board.columns.forEach((col: any) => {
            totalTasks += col.tasks.length;
            if (col.name.toLowerCase() === 'done') {
              completedTasks += col.tasks.length;
            }
          });
        } catch (e) {
          // Board might not be created or empty
        }
      }
      setTasksCount(totalTasks);
      setCompletedTasksCount(completedTasks);

      // Fetch Events
      const evs = await api.event.list(currentWorkspace.id);
      setEvents(evs.slice(0, 3)); // Display top 3 upcoming

      // Fetch Activities
      const acts = await api.collaboration.activityLogs(currentWorkspace.id);
      setActivities(acts.slice(0, 5)); // Display top 5 recent
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [currentWorkspace]);

  const handleCreateProject = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!currentWorkspace || !projectName.trim()) return;
    try {
      await api.project.create(currentWorkspace.id, {
        name: projectName,
        description: projectDesc
      });
      setShowProjectModal(false);
      setProjectName('');
      setProjectDesc('');
      fetchData();
    } catch (err) {
      console.error(err);
    }
  };

  const handleCreateEvent = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!currentWorkspace || !eventTitle.trim() || !eventStart || !eventEnd) return;
    try {
      await api.event.create(currentWorkspace.id, {
        title: eventTitle,
        description: eventDesc,
        startTime: new Date(eventStart).toISOString(),
        endTime: new Date(eventEnd).toISOString(),
        location: eventLocation,
        attendeeIds: []
      });
      setShowEventModal(false);
      setEventTitle('');
      setEventDesc('');
      setEventStart('');
      setEventEnd('');
      setEventLocation('');
      fetchData();
    } catch (err) {
      console.error(err);
    }
  };

  if (!currentWorkspace) {
    return (
      <div className="h-full flex flex-col items-center justify-center text-center p-8">
        <div className="w-16 h-16 rounded-full bg-slate-800 flex items-center justify-center mb-4 text-slate-500">
          <Briefcase className="w-8 h-8" />
        </div>
        <h2 className="text-xl font-bold text-slate-200">No Workspace Selected</h2>
        <p className="text-sm text-slate-500 max-w-sm mt-2">
          Please select an existing workspace from the sidebar switcher or create a new one to begin.
        </p>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="h-full flex items-center justify-center">
        <span className="w-8 h-8 border-4 border-indigo-500/20 border-t-indigo-500 rounded-full animate-spin" />
      </div>
    );
  }

  const completionRate = tasksCount > 0 ? Math.round((completedTasksCount / tasksCount) * 100) : 0;

  return (
    <div className="space-y-8 animate-fade-in">
      {/* Top Banner */}
      <div className="glass-panel p-6 rounded-2xl flex flex-col md:flex-row md:items-center justify-between gap-4 border border-slate-200">
        <div>
          <h2 className="text-2xl font-bold text-slate-850">Welcome, {user?.fullName}!</h2>
          <p className="text-sm text-slate-500 mt-1">Here is a quick summary of what is happening in {currentWorkspace.name}.</p>
        </div>
        <div className="flex gap-3">
          <button 
            onClick={() => setShowProjectModal(true)}
            className="flex items-center px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-sm shadow-lg shadow-indigo-600/10 active:scale-95 transition-all cursor-pointer"
          >
            <Plus className="w-4 h-4 mr-2" />
            New Project
          </button>
          <button 
            onClick={() => setShowEventModal(true)}
            className="flex items-center px-4 py-2 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-200 font-semibold text-sm border border-slate-700/50 active:scale-95 transition-all cursor-pointer"
          >
            <Calendar className="w-4 h-4 mr-2" />
            Schedule Event
          </button>
        </div>
      </div>

      {/* Metrics Cards Grid */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="glass-card glass-card-hover p-6 rounded-2xl flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-indigo-500/10 flex items-center justify-center text-indigo-400">
            <Briefcase className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs text-slate-500 font-semibold uppercase tracking-wider">Active Projects</p>
            <h3 className="text-2xl font-bold text-slate-200 mt-0.5">{projectsCount}</h3>
          </div>
        </div>

        <div className="glass-card glass-card-hover p-6 rounded-2xl flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-amber-500/10 flex items-center justify-center text-amber-400">
            <Clock className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs text-slate-500 font-semibold uppercase tracking-wider">Total Tasks</p>
            <h3 className="text-2xl font-bold text-slate-200 mt-0.5">{tasksCount}</h3>
          </div>
        </div>

        <div className="glass-card glass-card-hover p-6 rounded-2xl flex items-center gap-4">
          <div className="w-12 h-12 rounded-xl bg-emerald-500/10 flex items-center justify-center text-emerald-400">
            <CheckCircle className="w-6 h-6" />
          </div>
          <div>
            <p className="text-xs text-slate-500 font-semibold uppercase tracking-wider">Completed Tasks</p>
            <h3 className="text-2xl font-bold text-slate-200 mt-0.5">{completedTasksCount}</h3>
          </div>
        </div>

        <div className="glass-card glass-card-hover p-6 rounded-2xl">
          <div className="flex justify-between items-center mb-2">
            <p className="text-xs text-slate-500 font-semibold uppercase tracking-wider">Task Completion Rate</p>
            <span className="text-xs text-emerald-400 font-bold flex items-center">
              <TrendingUp className="w-3.5 h-3.5 mr-0.5" />
              {completionRate}%
            </span>
          </div>
          <div className="w-full bg-slate-200 rounded-full h-2">
            <div 
              className="bg-indigo-600 h-2 rounded-full transition-all duration-500" 
              style={{ width: `${completionRate}%` }} 
            />
          </div>
        </div>
      </div>

      {/* Main Dashboard Layout */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left Column - Upcoming Events */}
        <div className="glass-panel p-6 rounded-2xl border border-slate-800/60 lg:col-span-1 flex flex-col">
          <h3 className="text-base font-bold text-slate-200 mb-4 flex items-center">
            <Calendar className="w-4 h-4 mr-2 text-indigo-400" />
            Upcoming Events
          </h3>
          <div className="flex-1 space-y-4">
            {events.length === 0 ? (
              <div className="h-full flex flex-col items-center justify-center text-center p-6 border border-dashed border-slate-800/80 rounded-xl">
                <p className="text-xs text-slate-500">No events scheduled.</p>
                <button 
                  onClick={() => setShowEventModal(true)}
                  className="mt-2 text-xs text-indigo-400 hover:text-indigo-300 font-medium cursor-pointer"
                >
                  Create one now
                </button>
              </div>
            ) : (
              events.map(ev => (
                <div key={ev.id} className="p-3 bg-slate-900/40 border border-slate-800/60 rounded-xl space-y-2">
                  <h4 className="text-sm font-semibold text-slate-200">{ev.title}</h4>
                  <p className="text-xs text-slate-400 line-clamp-2">{ev.description}</p>
                  <div className="flex flex-wrap gap-x-3 gap-y-1 pt-1 text-[10px] text-slate-500">
                    <span className="flex items-center">
                      <Clock className="w-3.5 h-3.5 mr-1 text-slate-400" />
                      {new Date(ev.startTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                    </span>
                    {ev.location && (
                      <span className="flex items-center">
                        <MapPin className="w-3.5 h-3.5 mr-1 text-slate-400" />
                        {ev.location}
                      </span>
                    )}
                  </div>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Right Column - Recent Activity */}
        <div className="glass-panel p-6 rounded-2xl border border-slate-800/60 lg:col-span-2">
          <h3 className="text-base font-bold text-slate-200 mb-4 flex items-center">
            <Activity className="w-4 h-4 mr-2 text-indigo-400" />
            Recent Activity
          </h3>
          <div className="space-y-4">
            {activities.length === 0 ? (
              <div className="p-6 text-center text-xs text-slate-500">
                No recent workspace activity.
              </div>
            ) : (
              activities.map(act => (
                <div key={act.id} className="flex items-start gap-4 p-3 rounded-xl hover:bg-slate-900/20 transition-all border border-transparent hover:border-slate-800/40">
                  <div className="w-8 h-8 rounded-full bg-slate-800 flex items-center justify-center font-bold text-xs text-slate-400 shrink-0">
                    {act.user ? act.user.fullName.substring(0, 2).toUpperCase() : 'SYS'}
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="text-xs text-slate-300">
                      <span className="font-bold text-slate-200 mr-1">
                        {act.user ? act.user.fullName : 'System'}
                      </span>
                      {act.details}
                    </p>
                    <span className="text-[10px] text-slate-500 mt-1 block">
                      {new Date(act.createdAt).toLocaleDateString()} at {new Date(act.createdAt).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                    </span>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>

      {/* Create Project Modal */}
      {showProjectModal && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4 animate-fade-in">
          <div className="bg-[#0f1115] border border-slate-800 rounded-xl max-w-md w-full shadow-2xl p-6 relative">
            <h3 className="text-lg font-bold text-slate-100 mb-4">Create New Project</h3>
            <form onSubmit={handleCreateProject} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Project Name</label>
                <input 
                  type="text" 
                  value={projectName} 
                  onChange={(e) => setProjectName(e.target.value)}
                  placeholder="e.g. Website Redesign, Marketing Campaign"
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm"
                  required
                />
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Description</label>
                <textarea 
                  value={projectDesc} 
                  onChange={(e) => setProjectDesc(e.target.value)}
                  placeholder="Brief summary of project goals..."
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm h-24 resize-none"
                />
              </div>
              <div className="flex justify-end space-x-3 pt-2">
                <button 
                  type="button" 
                  onClick={() => setShowProjectModal(false)}
                  className="px-4 py-2 rounded-lg text-sm font-medium text-slate-400 hover:text-slate-200 transition-colors"
                >
                  Cancel
                </button>
                <button 
                  type="submit" 
                  className="px-4 py-2 rounded-lg text-sm font-semibold text-white bg-indigo-600 hover:bg-indigo-500 hover:shadow-lg active:scale-95 transition-all cursor-pointer"
                >
                  Create Project
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Create Event Modal */}
      {showEventModal && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4 animate-fade-in">
          <div className="bg-[#0f1115] border border-slate-800 rounded-xl max-w-md w-full shadow-2xl p-6 relative">
            <h3 className="text-lg font-bold text-slate-100 mb-4">Schedule Workspace Event</h3>
            <form onSubmit={handleCreateEvent} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Event Title</label>
                <input 
                  type="text" 
                  value={eventTitle} 
                  onChange={(e) => setEventTitle(e.target.value)}
                  placeholder="e.g. Sync Meeting, Project Kickoff"
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm"
                  required
                />
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Description</label>
                <textarea 
                  value={eventDesc} 
                  onChange={(e) => setEventDesc(e.target.value)}
                  placeholder="Agenda details..."
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm h-20 resize-none"
                />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Start Time</label>
                  <input 
                    type="datetime-local" 
                    value={eventStart} 
                    onChange={(e) => setEventStart(e.target.value)}
                    className="w-full glass-input px-3 py-2 rounded-lg text-sm"
                    required
                  />
                </div>
                <div>
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">End Time</label>
                  <input 
                    type="datetime-local" 
                    value={eventEnd} 
                    onChange={(e) => setEventEnd(e.target.value)}
                    className="w-full glass-input px-3 py-2 rounded-lg text-sm"
                    required
                  />
                </div>
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Location</label>
                <input 
                  type="text" 
                  value={eventLocation} 
                  onChange={(e) => setEventLocation(e.target.value)}
                  placeholder="e.g. Meeting Room A, Google Meet Link"
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm"
                />
              </div>
              <div className="flex justify-end space-x-3 pt-2">
                <button 
                  type="button" 
                  onClick={() => setShowEventModal(false)}
                  className="px-4 py-2 rounded-lg text-sm font-medium text-slate-400 hover:text-slate-200 transition-colors"
                >
                  Cancel
                </button>
                <button 
                  type="submit" 
                  className="px-4 py-2 rounded-lg text-sm font-semibold text-white bg-indigo-600 hover:bg-indigo-500 hover:shadow-lg active:scale-95 transition-all cursor-pointer"
                >
                  Schedule Event
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
