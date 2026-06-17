import React, { useState, useEffect } from 'react';
import { api } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { 
  X, 
  Calendar, 
  AlertTriangle, 
  Users, 
  CheckSquare, 
  MessageSquare,
  Trash,
  Check
} from 'lucide-react';
import confetti from 'canvas-confetti';

interface TaskModalProps {
  taskId: string;
  onClose: () => void;
  onUpdated: () => void;
}

export const TaskModal: React.FC<TaskModalProps> = ({ taskId, onClose, onUpdated }) => {
  const { currentWorkspace } = useAuth();
  
  const [loading, setLoading] = useState(true);
  
  // Fields for edits
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [priority, setPriority] = useState('MEDIUM');
  const [dueDate, setDueDate] = useState('');
  const [selectedAssignees, setSelectedAssignees] = useState<string[]>([]);
  
  // Members List
  const [members, setMembers] = useState<any[]>([]);
  
  // Subtasks
  const [subtasks, setSubtasks] = useState<any[]>([]);
  const [newSubtaskTitle, setNewSubtaskTitle] = useState('');
  
  // Comments
  const [comments, setComments] = useState<any[]>([]);
  const [newCommentContent, setNewCommentContent] = useState('');


  const loadTaskDetails = async () => {
    if (!currentWorkspace) return;
    setLoading(true);
    try {
      // Wait, we also have updateTask and other methods. To keep it fast, we can fetch comments and subtasks, and we can find the task by checking the project board.
      // Ah! We can search all projects of the workspace, fetch their boards, and find the task!
      // To make it simpler, we can fetch task details from the board if we pass project ID, or let's fetch board of project.
      // Wait! Let's check `TaskController.java`. We have:
      // - `PUT /api/tasks/{taskId}` -> returns TaskDto.
      // So we can fetch/update task details by calling updateTask with empty payload! That will return the current TaskDto!
      const currentTask = await api.task.updateTask(taskId, {});
      setTitle(currentTask.title);
      setDescription(currentTask.description || '');
      setPriority(currentTask.priority);
      setDueDate(currentTask.dueDate ? currentTask.dueDate.substring(0, 16) : '');
      setSelectedAssignees(currentTask.assignees ? currentTask.assignees.map((a: any) => a.id) : []);

      // Fetch workspace members
      const wMembers = await api.workspace.members(currentWorkspace.id);
      setMembers(wMembers);

      // Fetch Subtasks
      const subs = await api.task.getSubtasks(taskId);
      setSubtasks(subs);

      // Fetch Comments
      const comms = await api.collaboration.comments(taskId);
      setComments(comms);
    } catch (e) {
      console.error(e);
      onClose();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTaskDetails();
  }, [taskId]);

  const handleSaveChanges = async () => {
    try {
      await api.task.updateTask(taskId, {
        title,
        description,
        priority,
        dueDate: dueDate ? new Date(dueDate).toISOString() : null,
        assigneeIds: selectedAssignees
      });
      onUpdated();
    } catch (e) {
      console.error(e);
    }
  };

  const handleAddSubtask = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newSubtaskTitle.trim()) return;
    try {
      await api.task.createSubtask(taskId, { title: newSubtaskTitle });
      setNewSubtaskTitle('');
      const subs = await api.task.getSubtasks(taskId);
      setSubtasks(subs);
      onUpdated();
    } catch (e) {
      console.error(e);
    }
  };

  const handleToggleSubtask = async (subtaskId: string, isCompleted: boolean) => {
    try {
      await api.task.toggleSubtask(subtaskId, isCompleted);
      setSubtasks(prev => prev.map(s => s.id === subtaskId ? { ...s, isCompleted } : s));
      if (isCompleted) {
        confetti({
          particleCount: 50,
          spread: 40,
          origin: { y: 0.8 }
        });
      }
      onUpdated();
    } catch (e) {
      console.error(e);
    }
  };

  const handleDeleteSubtask = async (subtaskId: string) => {
    try {
      await api.task.deleteSubtask(subtaskId);
      setSubtasks(prev => prev.filter(s => s.id !== subtaskId));
      onUpdated();
    } catch (e) {
      console.error(e);
    }
  };

  const handleAddComment = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newCommentContent.trim()) return;
    try {
      await api.collaboration.addComment(taskId, { content: newCommentContent });
      setNewCommentContent('');
      const comms = await api.collaboration.comments(taskId);
      setComments(comms);
    } catch (e) {
      console.error(e);
    }
  };

  const handleDeleteTask = async () => {
    if (!window.confirm('Are you sure you want to delete this task?')) return;
    try {
      await api.task.deleteTask(taskId);
      onUpdated();
      onClose();
    } catch (e) {
      console.error(e);
    }
  };

  const toggleAssignee = (userId: string) => {
    setSelectedAssignees(prev => 
      prev.includes(userId) ? prev.filter(id => id !== userId) : [...prev, userId]
    );
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4 animate-fade-in">
        <div className="bg-[#0f1115] border border-slate-800 rounded-xl p-8 shadow-2xl flex flex-col items-center justify-center min-w-80">
          <span className="w-8 h-8 border-4 border-indigo-500/20 border-t-indigo-500 rounded-full animate-spin mb-3" />
          <p className="text-xs text-slate-500">Loading details...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4 overflow-y-auto animate-fade-in">
      <div className="bg-[#0f1115] border border-slate-800/80 rounded-2xl max-w-4xl w-full shadow-2xl overflow-hidden flex flex-col max-h-[90vh]">
        {/* Header */}
        <div className="p-6 border-b border-slate-800/40 flex items-center justify-between bg-slate-900/20">
          <h3 className="text-lg font-bold text-slate-100 truncate flex-1">
            Task details
          </h3>
          <div className="flex items-center space-x-3">
            <button
              onClick={handleDeleteTask}
              className="p-2 rounded-lg text-slate-500 hover:text-rose-400 hover:bg-rose-500/10 transition-colors"
              title="Delete Task"
            >
              <Trash className="w-4 h-4" />
            </button>
            <button
              onClick={onClose}
              className="p-2 rounded-lg text-slate-500 hover:text-slate-200 hover:bg-slate-800/60 transition-colors"
            >
              <X className="w-5 h-5" />
            </button>
          </div>
        </div>

        {/* Content Body */}
        <div className="flex-1 overflow-y-auto p-6 grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Main info (Left 2 cols) */}
          <div className="lg:col-span-2 space-y-6">
            <div>
              <label className="block text-[10px] font-bold text-slate-500 uppercase tracking-widest mb-1.5">Task Title</label>
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                onBlur={handleSaveChanges}
                className="w-full glass-input px-3.5 py-2 rounded-lg text-sm font-semibold"
              />
            </div>

            <div>
              <label className="block text-[10px] font-bold text-slate-500 uppercase tracking-widest mb-1.5">Description</label>
              <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                onBlur={handleSaveChanges}
                placeholder="Add a detailed description..."
                className="w-full glass-input px-3.5 py-2.5 rounded-lg text-sm h-28 resize-none"
              />
            </div>

            {/* Checklist */}
            <div className="space-y-3">
              <h4 className="text-xs font-bold text-slate-400 uppercase tracking-wider flex items-center">
                <CheckSquare className="w-4 h-4 mr-1.5 text-indigo-400" />
                Subtasks
              </h4>
              <div className="space-y-2">
                {subtasks.map(sub => (
                  <div key={sub.id} className="flex items-center justify-between p-2.5 bg-slate-900/30 border border-slate-800/40 rounded-xl group transition-all">
                    <label className="flex items-center space-x-3 cursor-pointer select-none">
                      <input
                        type="checkbox"
                        checked={sub.isCompleted}
                        onChange={(e) => handleToggleSubtask(sub.id, e.target.checked)}
                        className="rounded border-slate-700 text-indigo-600 focus:ring-indigo-500/30 focus:ring-offset-0 bg-slate-900 w-4 h-4"
                      />
                      <span className={`text-xs ${sub.isCompleted ? 'line-through text-slate-500' : 'text-slate-300'}`}>
                        {sub.title}
                      </span>
                    </label>
                    <button
                      onClick={() => handleDeleteSubtask(sub.id)}
                      className="p-1 rounded text-slate-500 hover:text-rose-400 hover:bg-rose-500/10 opacity-0 group-hover:opacity-100 transition-all cursor-pointer"
                    >
                      <Trash className="w-3.5 h-3.5" />
                    </button>
                  </div>
                ))}
              </div>
              <form onSubmit={handleAddSubtask} className="flex gap-2">
                <input
                  type="text"
                  value={newSubtaskTitle}
                  onChange={(e) => setNewSubtaskTitle(e.target.value)}
                  placeholder="Add a checklist item..."
                  className="flex-1 glass-input px-3.5 py-1.5 rounded-lg text-xs"
                />
                <button
                  type="submit"
                  className="px-3.5 py-1.5 bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700/50 rounded-lg text-xs font-semibold cursor-pointer"
                >
                  Add
                </button>
              </form>
            </div>

            {/* Comments Section */}
            <div className="space-y-4 pt-4 border-t border-slate-800/40">
              <h4 className="text-xs font-bold text-slate-400 uppercase tracking-wider flex items-center">
                <MessageSquare className="w-4 h-4 mr-1.5 text-indigo-400" />
                Comments
              </h4>
              <div className="space-y-4 max-h-60 overflow-y-auto pr-2">
                {comments.map(c => (
                  <div key={c.id} className="flex gap-3">
                    <div className="w-8 h-8 rounded-full bg-slate-800 border border-slate-700/60 flex items-center justify-center font-bold text-xs text-indigo-400 shrink-0">
                      {c.user.fullName.substring(0,2).toUpperCase()}
                    </div>
                    <div className="flex-1 bg-slate-900/40 border border-slate-800/60 rounded-xl p-3 text-xs">
                      <div className="flex justify-between items-center mb-1">
                        <span className="font-bold text-slate-200">{c.user.fullName}</span>
                        <span className="text-[10px] text-slate-500">
                          {new Date(c.createdAt).toLocaleDateString()}
                        </span>
                      </div>
                      <p className="text-slate-300 leading-relaxed">{c.content}</p>
                    </div>
                  </div>
                ))}
              </div>
              <form onSubmit={handleAddComment} className="flex gap-2 pt-2">
                <input
                  type="text"
                  value={newCommentContent}
                  onChange={(e) => setNewCommentContent(e.target.value)}
                  placeholder="Write a comment..."
                  className="flex-1 glass-input px-3.5 py-2 rounded-lg text-xs"
                />
                <button
                  type="submit"
                  className="px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white rounded-lg text-xs font-semibold cursor-pointer"
                >
                  Send
                </button>
              </form>
            </div>
          </div>

          {/* Properties Panel (Right Col) */}
          <div className="space-y-5 bg-slate-900/10 p-5 rounded-2xl border border-slate-800/40">
            <div>
              <label className="block text-[10px] font-bold text-slate-500 uppercase tracking-widest mb-1.5 flex items-center">
                <AlertTriangle className="w-3.5 h-3.5 mr-1.5" />
                Priority
              </label>
              <select
                value={priority}
                onChange={(e) => {
                  setPriority(e.target.value);
                  setTimeout(handleSaveChanges, 0);
                }}
                className="w-full glass-input px-3.5 py-2 rounded-lg text-xs font-medium cursor-pointer"
              >
                <option value="LOW" className="bg-[#0f1115]">Low</option>
                <option value="MEDIUM" className="bg-[#0f1115]">Medium</option>
                <option value="HIGH" className="bg-[#0f1115]">High</option>
                <option value="URGENT" className="bg-[#0f1115]">Urgent</option>
              </select>
            </div>

            <div>
              <label className="block text-[10px] font-bold text-slate-500 uppercase tracking-widest mb-1.5 flex items-center">
                <Calendar className="w-3.5 h-3.5 mr-1.5" />
                Due Date
              </label>
              <input
                type="datetime-local"
                value={dueDate}
                onChange={(e) => {
                  setDueDate(e.target.value);
                  setTimeout(handleSaveChanges, 0);
                }}
                className="w-full glass-input px-3 py-2 rounded-lg text-xs"
              />
            </div>

            <div>
              <label className="block text-[10px] font-bold text-slate-500 uppercase tracking-widest mb-1.5 flex items-center">
                <Users className="w-3.5 h-3.5 mr-1.5" />
                Assignees
              </label>
              <div className="max-h-40 overflow-y-auto border border-slate-800/60 rounded-lg p-2 space-y-1.5">
                {members.map(member => {
                  const isAssigned = selectedAssignees.includes(member.user.id);
                  return (
                    <button
                      key={member.id}
                      onClick={() => {
                        toggleAssignee(member.user.id);
                        setTimeout(handleSaveChanges, 0);
                      }}
                      className="w-full flex items-center justify-between p-1.5 rounded text-left text-xs transition-colors hover:bg-slate-800/40"
                    >
                      <div className="flex items-center min-w-0">
                        <div className="w-5 h-5 rounded-full bg-slate-800 flex items-center justify-center font-bold text-[9px] text-slate-400 shrink-0 mr-2">
                          {member.user.fullName.substring(0, 2).toUpperCase()}
                        </div>
                        <span className="truncate text-slate-300">{member.user.fullName}</span>
                      </div>
                      {isAssigned && <Check className="w-3.5 h-3.5 text-indigo-400 shrink-0" />}
                    </button>
                  );
                })}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
