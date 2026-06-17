import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { LogIn } from 'lucide-react';

const Login: React.FC = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    if (!email || !password) {
      setError('Please fill in all fields');
      return;
    }
    setLoading(true);
    try {
      await login({ email, password });
      navigate('/');
    } catch (err: any) {
      setError(err.message || 'Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-[#f6f5f4] p-4 relative overflow-hidden font-sans">
      <div className="w-full max-w-md glass-panel p-8 rounded-2xl notion-shadow-soft relative z-10 animate-fade-in">
        <div className="text-center mb-8">
          <div className="w-12 h-12 rounded-xl bg-[#0075de] flex items-center justify-center font-bold text-white text-xl mx-auto mb-4">
            SW
          </div>
          <h2 className="text-2xl font-bold text-[#1a1917]">
            Welcome Back
          </h2>
          <p className="text-xs text-[#615d59] mt-1">Sign in to manage your workspaces</p>
        </div>

        {error && (
          <div className="mb-4 text-xs bg-rose-50 border border-rose-200 text-rose-600 p-3 rounded-lg">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-semibold text-[#615d59] uppercase tracking-wider mb-1.5">
              Email Address
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="you@example.com"
              className="w-full glass-input px-4 py-2.5 rounded-lg text-sm text-[#1a1917]"
              required
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-[#615d59] uppercase tracking-wider mb-1.5">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              className="w-full glass-input px-4 py-2.5 rounded-lg text-sm text-[#1a1917]"
              required
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full flex items-center justify-center py-2.5 px-4 rounded-lg bg-[#0075de] hover:bg-[#005bab] text-white font-semibold text-sm active:scale-98 transition-all disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer mt-6"
          >
            {loading ? (
              <span className="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin" />
            ) : (
              <>
                <LogIn className="w-4 h-4 mr-2" />
                Sign In
              </>
            )}
          </button>
        </form>

        <div className="text-center mt-6">
          <p className="text-xs text-[#615d59]">
            Don't have an account?{' '}
            <Link to="/register" className="text-[#0075de] hover:underline font-medium transition-colors">
              Create an account
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;
