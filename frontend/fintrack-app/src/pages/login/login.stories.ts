import type { Meta, StoryObj } from '@storybook/angular';
import { LoginPage } from './login.component';

const meta: Meta<LoginPage> = {
  title: 'Components/LoginPage',
  component: LoginPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<LoginPage>;

export const Primary: Story = {
  args: {},
};
