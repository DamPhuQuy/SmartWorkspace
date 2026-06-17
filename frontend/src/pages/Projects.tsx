import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { api } from '../services/api';
import { 
  Folder, 
  Plus, 
  Calendar, 
  PlusCircle,
  Trash
} from 'lucide-react';
import { TaskModal } from '../components/TaskModal';

interface Project {
  id: string;
  name: string;
  description: string;
  status: string;
}

interface Column {
  id: string;
  name: string;
  position: number;
  tasks: any[];
}

interface Board {
  id: string;
  name: string;
  columns: Column[];
}

const Projects: React.FC = () => {
  const { currentWorkspace } = useAuth();
  
  const [projects, setProjects] = useState<Project[]>([]);
  const [selectedProject, setSelectedProject] = useState<Project | null>(null);
  const [board, setBoard] = useState<Board | null>(null);

  // Modal States
  const [showProjectModal, setShowProjectModal] = useState(false);
  const [projectName, setProjectName] = useState('');
  const [projectDesc, setProjectDesc] = useState('');

  const [showColumnInput, setShowColumnInput] = useState(false);
  const [columnName, setColumnName] = useState('');

  const [showTaskModal, setShowTaskModal] = useState(false);
  const [activeTaskId, setActiveTaskId] = useState<string | null>(null);
  
  // Task Quick Create Inline States
  const [showTaskInputColId, setShowTaskInputColId] = useState<string | null>(null);
  const [newTaskTitle, setNewTaskTitle] = useState('');

  const fetchProjects = async () => {
    if (!currentWorkspace) return;
    try {
      const list = await api.project.list(currentWorkspace.id);
      setProjects(list);
      if (list.length > 0) {
        // Automatically select the first project if none is currently selected
        const currentId = selectedProject ? selectedProject.id : null;
        const stillExists = list.find(p => p.id === currentId);
        if (stillExists) {
          setSelectedProject(stillExists);
        } else {
          setSelectedProject(list[0]);
        }
      } else {
        setSelectedProject(null);
        setBoard(null);
      }
    } catch (e) {
      console.error(e);
    }
  };

  const fetchBoard = async (projectId: string) => {
    try {
      const boardDetails = await api.task.getBoard(projectId);
      setBoard(boardDetails);
    } catch (e) {
      console.error(e);
      setBoard(null);
    }
  };

  useEffect(() => {
    fetchProjects();
  }, [currentWorkspace]);

  useEffect(() => {
    if (selectedProject) {
      fetchBoard(selectedProject.id);
    }
  }, [selectedProject]);

  const handleCreateProject = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!currentWorkspace || !projectName.trim()) return;
    try {
      const p = await api.project.create(currentWorkspace.id, {
        name: projectName,
        description: projectDesc
      });
      setShowProjectModal(false);
      setProjectName('');
      setProjectDesc('');
      await fetchProjects();
      setSelectedProject(p);
    } catch (err) {
      console.error(err);
    }
  };

  const handleDeleteProject = async (projectId: string) => {
    if (!window.confirm('Are you sure you want to delete this project?')) return;
    try {
      await api.project.delete(projectId);
      fetchProjects();
    } catch (e) {
      console.error(e);
    }
  };

  const handleCreateColumn = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!board || !columnName.trim()) return;
    try {
      await api.task.createColumn(board.id, columnName);
      setColumnName('');
      setShowColumnInput(false);
      fetchBoard(selectedProject!.id);
    } catch (e) {
      console.error(e);
    }
  };

  const handleQuickTaskCreate = async (columnId: string) => {
    if (!newTaskTitle.trim() || !selectedProject) return;
    try {
      await api.task.createTask(selectedProject.id, columnId, {
        title: newTaskTitle,
        description: '',
        priority: 'MEDIUM',
        assigneeIds: []
      });
      setNewTaskTitle('');
      setShowTaskInputColId(null);
      fetchBoard(selectedProject.id);
    } catch (e) {
      console.error(e);
    }
  };

  // Drag and Drop Logic using native HTML5 DnD APIs
  const handleDragStart = (e: React.DragEvent, taskId: string) => {
    e.dataTransfer.setData('text/plain', taskId);
    e.dataTransfer.effectAllowed = 'move';
  };

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
  };

  const handleDrop = async (e: React.DragEvent, targetColumnId: string) => {
    e.preventDefault();
    const taskId = e.dataTransfer.getData('text/plain');
    if (!taskId || !selectedProject) return;
    try {
      await api.task.updateTask(taskId, { columnId: targetColumnId });
      fetchBoard(selectedProject.id);
    } catch (err) {
      console.error('Failed to move task', err);
    }
  };

  if (!currentWorkspace) {
    return (
      <div className="h-full flex items-center justify-center">
        <p className="text-slate-500">Please select a workspace first.</p>
      </div>
    );
  }

  const getPriorityColor = (priority: string) => {
    switch (priority) {
      case 'URGENT': return 'bg-rose-500/10 text-rose-400 border border-rose-500/20';
      case 'HIGH': return 'bg-orange-500/10 text-orange-400 border border-orange-500/20';
      case 'MEDIUM': return 'bg-indigo-500/10 text-indigo-400 border border-indigo-500/20';
      default: return 'bg-slate-500/10 text-slate-400 border border-slate-700/30';
    }
  };

  return (
    <div className="h-full flex flex-col space-y-6 overflow-hidden max-h-[85vh] animate-fade-in">
      {/* Top action bar */}
      <div className="flex items-center justify-between shrink-0">
        <div>
          <h2 className="text-xl font-bold text-slate-100">Projects & Tasks</h2>
          <p className="text-xs text-slate-500 mt-1">Manage project boards, drag & drop cards, checklist item metrics.</p>
        </div>
        <button 
          onClick={() => setShowProjectModal(true)}
          className="flex items-center px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-xs shadow-lg active:scale-95 transition-all cursor-pointer"
        >
          <Plus className="w-4 h-4 mr-1.5" />
          Add Project
        </button>
      </div>

      {/* Projects List tabs */}
      <div className="flex gap-2 overflow-x-auto pb-2 border-b border-slate-800/40 shrink-0 select-none">
        {projects.map(p => (
          <div 
            key={p.id} 
            className={`flex items-center p-0.5 rounded-lg border transition-all ${
              selectedProject?.id === p.id 
                ? 'bg-indigo-600/10 border-indigo-500 text-slate-200' 
                : 'bg-slate-900/20 border-slate-800/80 text-slate-400 hover:border-slate-800 hover:text-slate-300'
            }`}
          >
            <button
              onClick={() => setSelectedProject(p)}
              className="flex items-center px-3 py-1.5 text-xs font-semibold cursor-pointer shrink-0"
            >
              <Folder className="w-3.5 h-3.5 mr-2 text-indigo-400" />
              {p.name}
            </button>
            <button
              onClick={() => handleDeleteProject(p.id)}
              className="p-1 rounded text-slate-600 hover:text-rose-400 hover:bg-rose-500/10 transition-colors cursor-pointer mr-1"
            >
              <Trash className="w-3 h-3" />
            </button>
          </div>
        ))}
      </div>

      {/* Kanban Board Container */}
      {selectedProject ? (
        <div className="flex-1 flex flex-col space-y-4 overflow-hidden min-h-0">
          {/* Board Actions */}
          <div className="flex items-center justify-between shrink-0">
            <h3 className="text-sm font-bold text-slate-300 flex items-center">
              {board?.name}
            </h3>
            
            {!showColumnInput ? (
              <button 
                onClick={() => setShowColumnInput(true)}
                className="text-xs text-indigo-400 hover:text-indigo-300 font-medium flex items-center cursor-pointer"
              >
                <PlusCircle className="w-4 h-4 mr-1" />
                Add Column
              </button>
            ) : (
              <form onSubmit={handleCreateColumn} className="flex gap-2 items-center">
                <input 
                  type="text" 
                  value={columnName}
                  onChange={(e) => setColumnName(e.target.value)}
                  placeholder="Column title..."
                  className="glass-input px-2.5 py-1 rounded text-xs"
                  required
                />
                <button 
                  type="submit" 
                  className="px-2.5 py-1 bg-indigo-600 hover:bg-indigo-500 text-white rounded text-xs font-semibold cursor-pointer"
                >
                  Create
                </button>
                <button 
                  type="button" 
                  onClick={() => setShowColumnInput(false)}
                  className="text-xs text-slate-400 hover:text-slate-200 cursor-pointer"
                >
                  Cancel
                </button>
              </form>
            )}
          </div>

          {/* Kanban Columns Grid */}
          <div className="flex-1 flex gap-6 overflow-x-auto pb-4 items-start select-none min-h-0">
            {board?.columns.map(col => (
              <div 
                key={col.id} 
                onDragOver={handleDragOver}
                onDrop={(e) => handleDrop(e, col.id)}
                className="w-80 max-h-full flex flex-col glass-card border border-slate-800/80 rounded-2xl shrink-0 p-4 min-h-[300px]"
              >
                {/* Column Header */}
                <div className="flex justify-between items-center mb-4 shrink-0">
                  <div className="flex items-center space-x-2">
                    <span className="text-xs font-bold text-slate-200">{col.name}</span>
                    <span className="text-[10px] px-1.5 py-0.5 rounded-full bg-slate-800 text-slate-400 font-semibold">
                      {col.tasks.length}
                    </span>
                  </div>
                  <button 
                    onClick={() => {
                      setShowTaskInputColId(col.id);
                      setNewTaskTitle('');
                    }}
                    className="p-1 rounded text-slate-500 hover:text-indigo-400 hover:bg-indigo-500/10 cursor-pointer"
                  >
                    <Plus className="w-3.5 h-3.5" />
                  </button>
                </div>

                {/* Column Body / Tasks list */}
                <div className="flex-1 overflow-y-auto space-y-3 pr-1 min-h-0">
                  {col.tasks.map(task => (
                    <div 
                      key={task.id} 
                      draggable
                      onDragStart={(e) => handleDragStart(e, task.id)}
                      onClick={() => {
                        setActiveTaskId(task.id);
                        setShowTaskModal(true);
                      }}
                      className="p-4 bg-slate-900/40 border border-slate-800/60 rounded-xl space-y-3 cursor-grab active:cursor-grabbing hover:border-slate-700/60 transition-all hover:bg-slate-900/60 select-none group"
                    >
                      <div className="flex items-start justify-between gap-2">
                        <h4 className="text-xs font-semibold text-slate-200 leading-normal line-clamp-2">
                          {task.title}
                        </h4>
                      </div>
                      
                      {task.description && (
                        <p className="text-[11px] text-slate-400 line-clamp-2">{task.description}</p>
                      )}

                      <div className="flex justify-between items-center pt-1">
                        <span className={`text-[9px] px-2 py-0.5 rounded-full font-bold uppercase ${getPriorityColor(task.priority)}`}>
                          {task.priority}
                        </span>

                        <div className="flex items-center space-x-2">
                          {task.dueDate && (
                            <span className="text-[9px] text-slate-500 flex items-center">
                              <Calendar className="w-3 h-3 mr-1" />
                              {new Date(task.dueDate).toLocaleDateString()}
                            </span>
                          )}
                          
                          {/* Avatars */}
                          <div className="flex -space-x-1.5 overflow-hidden">
                            {task.assignees?.map((assignee: any) => (
                              <div 
                                key={assignee.id} 
                                className="w-5 h-5 rounded-full bg-slate-800 border border-slate-900 flex items-center justify-center font-bold text-[8px] text-indigo-400"
                                title={assignee.fullName}
                              >
                                {assignee.fullName.substring(0, 2).toUpperCase()}
                              </div>
                            ))}
                          </div>
                        </div>
                      </div>
                    </div>
                  ))}

                  {showTaskInputColId === col.id && (
                    <div className="p-3 bg-slate-900/20 border border-slate-800 rounded-xl space-y-2.5">
                      <input 
                        type="text" 
                        value={newTaskTitle}
                        onChange={(e) => setNewTaskTitle(e.target.value)}
                        placeholder="Task title..."
                        className="w-full glass-input px-2.5 py-1.5 rounded text-xs"
                        autoFocus
                      />
                      <div className="flex space-x-2">
                        <button 
                          onClick={() => handleQuickTaskCreate(col.id)}
                          className="px-2.5 py-1 bg-indigo-600 hover:bg-indigo-500 text-white rounded text-[10px] font-semibold cursor-pointer"
                        >
                          Add
                        </button>
                        <button 
                          onClick={() => setShowTaskInputColId(null)}
                          className="px-2.5 py-1 text-slate-400 hover:text-slate-200 text-[10px] cursor-pointer"
                        >
                          Cancel
                        </button>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="flex-1 flex flex-col items-center justify-center border border-dashed border-slate-800 rounded-2xl p-12 text-center">
          <Folder className="w-12 h-12 text-slate-600 mb-4" />
          <h3 className="text-slate-300 font-bold">No Projects Found</h3>
          <p className="text-xs text-slate-500 max-w-sm mt-1">
            Build your project spaces, initialize milestones, and configure customized task flows.
          </p>
          <button 
            onClick={() => setShowProjectModal(true)}
            className="mt-4 flex items-center px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-xs shadow-lg shadow-indigo-600/10 active:scale-95 transition-all cursor-pointer"
          >
            Create Your First Project
          </button>
        </div>
      )}

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

      {/* Detailed Task Details Modal */}
      {showTaskModal && activeTaskId && (
        <TaskModal
          taskId={activeTaskId}
          onClose={() => {
            setShowTaskModal(false);
            setActiveTaskId(null);
          }}
          onUpdated={() => {
            if (selectedProject) fetchBoard(selectedProject.id);
          }}
        />
      )}
    </div>
  );
};

export default Projects;
