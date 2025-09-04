export class StorageService {
  private static USER_KEY = 'user';
  private static TOKEN_KEY = 'token';

  static saveUser(user: { id: number; role: string }): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  static getUser(): { id: number; role: string } | null {
    const userStr = localStorage.getItem(this.USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
  }

  static saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  static getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  static clear(): void {
    localStorage.removeItem(this.USER_KEY);
    localStorage.removeItem(this.TOKEN_KEY);
  }

  static isAdminLoggedIn(): boolean {
    const user = this.getUser();
    return user?.role === 'ADMIN';
  }

  static isUserLoggedIn(): boolean {
    const user = this.getUser();
    return user?.role === 'USER';
  }
}
