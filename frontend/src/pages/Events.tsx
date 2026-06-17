import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { api } from '../services/api';
import { 
  Calendar, 
  Clock, 
  MapPin, 
  User, 
  PlusCircle, 
  Check, 
  X, 
  Trash
} from 'lucide-react';

interface WorkspaceEvent {
  id: string;
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  location: string;
  organizer: {
    id: string;
    fullName: string;
  } | null;
}

const Events: React.FC = () => {
  const { currentWorkspace, user } = useAuth();
  
  const [events, setEvents] = useState<WorkspaceEvent[]>([]);
  const [attendeesMap, setAttendeesMap] = useState<Record<string, any[]>>({});
  const [userRsvpMap, setUserRsvpMap] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(true);

  // Event creation
  const [showEventModal, setShowEventModal] = useState(false);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [location, setLocation] = useState('');
  const [workspaceMembers, setWorkspaceMembers] = useState<any[]>([]);
  const [selectedInvitees, setSelectedInvitees] = useState<string[]>([]);

  const fetchEvents = async () => {
    if (!currentWorkspace) return;
    setLoading(true);
    try {
      const list = await api.event.list(currentWorkspace.id);
      setEvents(list);

      // Fetch attendees and user RSVP for each event
      const tempAttendees: Record<string, any[]> = {};
      const tempRsvps: Record<string, string> = {};
      
      for (const ev of list) {
        try {
          const atts = await api.event.attendees(ev.id);
          tempAttendees[ev.id] = atts;
          // Find currently logged in user's RSVP status
          const myAtt = atts.find((a: any) => a.user.id === user?.id);
          if (myAtt) {
            tempRsvps[ev.id] = myAtt.status;
          }
        } catch (e) {
          // Suppress errors
        }
      }
      setAttendeesMap(tempAttendees);
      setUserRsvpMap(tempRsvps);

      // Fetch workspace members for invitation dropdown
      const members = await api.workspace.members(currentWorkspace.id);
      setWorkspaceMembers(members);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEvents();
  }, [currentWorkspace]);

  const handleCreateEvent = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!currentWorkspace || !title.trim() || !startTime || !endTime) return;
    try {
      await api.event.create(currentWorkspace.id, {
        title,
        description,
        startTime: new Date(startTime).toISOString(),
        endTime: new Date(endTime).toISOString(),
        location,
        attendeeIds: selectedInvitees
      });
      setShowEventModal(false);
      setTitle('');
      setDescription('');
      setStartTime('');
      setEndTime('');
      setLocation('');
      setSelectedInvitees([]);
      fetchEvents();
    } catch (err) {
      console.error(err);
    }
  };

  const handleRsvp = async (eventId: string, status: string) => {
    try {
      await api.event.rsvp(eventId, status);
      setUserRsvpMap(prev => ({ ...prev, [eventId]: status }));
      
      // Reload attendees list for this event
      const atts = await api.event.attendees(eventId);
      setAttendeesMap(prev => ({ ...prev, [eventId]: atts }));
    } catch (e) {
      console.error(e);
    }
  };

  const handleDeleteEvent = async (eventId: string) => {
    if (!window.confirm('Are you sure you want to delete this event?')) return;
    try {
      await api.event.delete(eventId);
      fetchEvents();
    } catch (e) {
      console.error(e);
    }
  };

  const toggleInvitee = (userId: string) => {
    setSelectedInvitees(prev => 
      prev.includes(userId) ? prev.filter(id => id !== userId) : [...prev, userId]
    );
  };

  if (!currentWorkspace) {
    return (
      <div className="h-full flex items-center justify-center">
        <p className="text-slate-500">Please select a workspace first.</p>
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

  return (
    <div className="space-y-6 animate-fade-in max-w-4xl mx-auto">
      {/* Top action bar */}
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-xl font-bold text-slate-100">Calendar & Events</h2>
          <p className="text-xs text-slate-500 mt-1">Schedule group syncs, track RSVP responses, coordinate locations.</p>
        </div>
        <button 
          onClick={() => setShowEventModal(true)}
          className="flex items-center px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-xs shadow-lg active:scale-95 transition-all cursor-pointer"
        >
          <PlusCircle className="w-4 h-4 mr-1.5" />
          Schedule Event
        </button>
      </div>

      {/* Events List */}
      <div className="space-y-6">
        {events.length === 0 ? (
          <div className="flex flex-col items-center justify-center border border-dashed border-slate-800 rounded-2xl p-12 text-center">
            <Calendar className="w-12 h-12 text-slate-600 mb-4" />
            <h3 className="text-slate-300 font-bold">No Events Scheduled</h3>
            <p className="text-xs text-slate-500 max-w-sm mt-1">
              Add shared group milestones, arrange scrum meetings, or register club activities.
            </p>
          </div>
        ) : (
          events.map(ev => {
            const myRsvp = userRsvpMap[ev.id] || 'PENDING';
            const attendees = attendeesMap[ev.id] || [];
            
            return (
              <div 
                key={ev.id} 
                className={`glass-panel p-6 rounded-2xl border flex flex-col md:flex-row justify-between gap-6 transition-all ${
                  myRsvp === 'ACCEPTED' 
                    ? 'border-emerald-500/20 bg-emerald-500/5' 
                    : myRsvp === 'DECLINED' 
                      ? 'border-rose-500/10 bg-rose-500/5' 
                      : 'border-slate-800/80 bg-slate-900/10'
                }`}
              >
                <div className="space-y-3 flex-1">
                  <div className="flex items-center space-x-2">
                    <h3 className="text-base font-bold text-slate-100">{ev.title}</h3>
                    {ev.organizer?.id === user?.id && (
                      <span className="text-[9px] px-1.5 py-0.5 rounded bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 font-semibold">
                        Host
                      </span>
                    )}
                  </div>
                  
                  <p className="text-xs text-slate-400 max-w-xl leading-relaxed">{ev.description}</p>
                  
                  <div className="flex flex-wrap gap-x-4 gap-y-1.5 text-xs text-slate-500 pt-1">
                    <span className="flex items-center">
                      <Clock className="w-4 h-4 mr-1.5 text-slate-400" />
                      {new Date(ev.startTime).toLocaleString()} - {new Date(ev.endTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                    </span>
                    {ev.location && (
                      <span className="flex items-center">
                        <MapPin className="w-4 h-4 mr-1.5 text-slate-400" />
                        {ev.location}
                      </span>
                    )}
                    <span className="flex items-center">
                      <User className="w-4 h-4 mr-1.5 text-slate-400" />
                      Organizer: {ev.organizer ? ev.organizer.fullName : 'System'}
                    </span>
                  </div>

                  {/* Attendees statuses list */}
                  <div className="pt-2">
                    <h4 className="text-[10px] font-bold text-slate-500 uppercase tracking-widest mb-1.5">Invited Members</h4>
                    <div className="flex flex-wrap gap-2">
                      {attendees.map(att => (
                        <span 
                          key={att.user.id} 
                          className={`text-[10px] px-2 py-0.5 rounded-full font-medium border flex items-center gap-1 ${
                            att.status === 'ACCEPTED' 
                              ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/20' 
                              : att.status === 'DECLINED'
                                ? 'bg-rose-500/10 text-rose-400 border-rose-500/20'
                                : 'bg-slate-800/40 text-slate-400 border-slate-700/30'
                          }`}
                        >
                          <span className="w-1.5 h-1.5 rounded-full bg-current" />
                          {att.user.fullName}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>

                {/* RSVP Selector */}
                <div className="flex flex-row md:flex-col justify-end items-center gap-3 self-center md:self-end">
                  <div className="flex gap-2">
                    <button
                      onClick={() => handleRsvp(ev.id, 'ACCEPTED')}
                      className={`px-3 py-1.5 rounded-lg text-xs font-semibold flex items-center transition-all cursor-pointer ${
                        myRsvp === 'ACCEPTED' 
                          ? 'bg-emerald-600 text-white hover:bg-emerald-500' 
                          : 'bg-slate-800/60 hover:bg-slate-800 text-slate-400 hover:text-slate-200 border border-slate-700/50'
                      }`}
                    >
                      <Check className="w-3.5 h-3.5 mr-1" />
                      Accept
                    </button>
                    <button
                      onClick={() => handleRsvp(ev.id, 'DECLINED')}
                      className={`px-3 py-1.5 rounded-lg text-xs font-semibold flex items-center transition-all cursor-pointer ${
                        myRsvp === 'DECLINED' 
                          ? 'bg-rose-600 text-white hover:bg-rose-500' 
                          : 'bg-slate-800/60 hover:bg-slate-800 text-slate-400 hover:text-slate-200 border border-slate-700/50'
                      }`}
                    >
                      <X className="w-3.5 h-3.5 mr-1" />
                      Decline
                    </button>
                  </div>
                  {ev.organizer?.id === user?.id && (
                    <button
                      onClick={() => handleDeleteEvent(ev.id)}
                      className="p-2 text-slate-500 hover:text-rose-400 hover:bg-rose-500/10 rounded-lg transition-colors cursor-pointer"
                      title="Cancel Event"
                    >
                      <Trash className="w-4 h-4" />
                    </button>
                  )}
                </div>
              </div>
            );
          })
        )}
      </div>

      {/* Schedule Event Modal */}
      {showEventModal && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4 animate-fade-in">
          <div className="bg-[#0f1115] border border-slate-800 rounded-xl max-w-md w-full shadow-2xl p-6 relative">
            <h3 className="text-lg font-bold text-slate-100 mb-4">Schedule Workspace Event</h3>
            <form onSubmit={handleCreateEvent} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Event Title</label>
                <input 
                  type="text" 
                  value={title} 
                  onChange={(e) => setTitle(e.target.value)}
                  placeholder="e.g. Sync Meeting, Project Kickoff"
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm"
                  required
                />
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Description</label>
                <textarea 
                  value={description} 
                  onChange={(e) => setDescription(e.target.value)}
                  placeholder="Agenda details..."
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm h-20 resize-none"
                />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Start Time</label>
                  <input 
                    type="datetime-local" 
                    value={startTime} 
                    onChange={(e) => setStartTime(e.target.value)}
                    className="w-full glass-input px-3 py-2 rounded-lg text-sm"
                    required
                  />
                </div>
                <div>
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">End Time</label>
                  <input 
                    type="datetime-local" 
                    value={endTime} 
                    onChange={(e) => setEndTime(e.target.value)}
                    className="w-full glass-input px-3 py-2 rounded-lg text-sm"
                    required
                  />
                </div>
              </div>
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Location</label>
                <input 
                  type="text" 
                  value={location} 
                  onChange={(e) => setLocation(e.target.value)}
                  placeholder="e.g. Meeting Room A, Google Meet Link"
                  className="w-full glass-input px-3.5 py-2 rounded-lg text-sm"
                />
              </div>
              
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1.5">Invite Members</label>
                <div className="max-h-32 overflow-y-auto border border-slate-800 rounded-lg p-2 space-y-1 bg-slate-950/20">
                  {workspaceMembers.map(member => (
                    <button
                      key={member.id}
                      type="button"
                      onClick={() => toggleInvitee(member.user.id)}
                      className="w-full flex items-center justify-between p-1.5 rounded text-left text-xs transition-colors hover:bg-slate-800/40"
                    >
                      <span>{member.user.fullName}</span>
                      {selectedInvitees.includes(member.user.id) && (
                        <Check className="w-3.5 h-3.5 text-indigo-400 shrink-0" />
                      )}
                    </button>
                  ))}
                </div>
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

export default Events;
